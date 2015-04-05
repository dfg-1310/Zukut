package com.android.zukut.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.AcceptCallOutput;
import com.android.zukut.bo.CallDetail;
import com.android.zukut.bo.GCMResponse;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.AppConstant;
import com.android.zukut.util.AppConstant.INTENT_EXTRAS;
import com.android.zukut.util.PreferenceKeeper;

/**
 * Class is used to show incoming call pop-up.
 * 
 */
public class IncomingCallPopUpActivity extends Activity implements
		OnClickListener {

	private static final String API_TAG = "IncomingCallPopUpActivity";
	private long fromUserId;
	private long chId;

	// private EditText introduceEditText;

	private ImageView userImageView;

	// private TextView usernameTextView;
	// private TextView placeTextView;
	// private TextView userAgaeTextView;
	// private TextView relationTextView;

	private Button declineButton;
	private Button acceptButton;

	// private TextView tag1_textvw;
	// private TextView tag2_textvw;

	private GCMResponse gcmResponse;

	private CallDetail callDetail;

	private String friendAddress;

	private int friendOnlineStatus;

	private Intent stopRingIntent;
	private static Handler missCallHandler;

	private Intent incomingCallPopUpScreenViewIntent;

	private TextView friendNameTextView;

	//
	// private LinearLayout tags_ln_lyout;
	// private RelativeLayout tag1_rl_lyout;
	// private RelativeLayout tag2_rl_lyout;
	// private ImageView tag1_imgvw;
	// private ImageView tag2_imgvw;

	/**
	 * Initializes the activity, manages the listeners of the UI components, get
	 * data from the intent and initializes receivers and handlers.
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_incoming_call_popup);
		getDataFromBundle();
		initViewControls();

		getCallDetails();
		initializeGcmResponseHandler();
		initializeReceiverIntent();
		manageScreenKeepOn();
		incomingCallPopUpScreenViewIntent = new Intent(
				AppConstant.getCallPopUpViewActionBroadCastAction());

		registerReceiver(
				finishIncomgCallPopScreenViewReceiver,
				new IntentFilter(AppConstant
						.getCallPopUpViewActionBroadCastAction()));

	}

	/**
	 * Method to manage screen keep on.
	 */
	private void manageScreenKeepOn() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	}

	/**
	 * Starts the Flurry agent session.
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * Stops the Flurry agent session.
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * Receiver to finish the incoming call pop up.
	 */
	private BroadcastReceiver finishIncomgCallPopScreenViewReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				IncomingCallPopUpActivity.this.finish();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Method to initialize the receiver to stop the ringtone.
	 */
	private void initializeReceiverIntent() {

		stopRingIntent = new Intent(AppConstant.getStopRingBroadCastAction());

	}

	/**
	 * Method to make an api call to get the call details.
	 */
	private void getCallDetails() {

		// TODO write sdcard log for calling flow:

		System.out.println(" get call details api.");

		// TODO :: call detail here api

		// caller = WeCamApiClient.getClient();
		String[] ids = gcmResponse.getIds().split(",");
		fromUserId = Long.parseLong(ids[0]);
		chId = Long.parseLong(ids[1]);

		// CallDetailInput input = new CallDetailInput(fromUserId, "1", chId,
		// new PreferenceKeeper(this).getUserId(), new PreferenceKeeper(
		// this).getToken());
		//
		// caller.get(IncomingCallPopUpActivity.this, 1,
		// WeCam_URLS.WC_CALLING_DETAIL_URL, false, input,
		// new CallDetailOutput(), API_NAME.callDetail, true);
		//

		PreferenceKeeper keeper = new PreferenceKeeper(this);

		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.callDtl(fromUserId, 0, "" + chId, keeper
						.getUserInfo().getId(),
						AppConstant.OPEN_TOK_API_SECRET, "1",
						new AppResponseListener<CallDetail>(CallDetail.class,
								IncomingCallPopUpActivity.this) {

							@Override
							public void onSuccess(CallDetail callDetail,
									Long serverTime) {

								IncomingCallPopUpActivity.this.callDetail = callDetail;
								if (callDetail.getCs().equalsIgnoreCase("OK")) {
									manageUI(callDetail);
								} else {
									showToast(callDetail.getCsMsg());
								}
							}

							@Override
							public void onError(ErrorObject error) {
								// TODO Auto-generated method stub
								showToast(error.getErrorMessage());
							}
						}), API_TAG);

	}

	/**
	 * Method to get data from the intent
	 */
	private void getDataFromBundle() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			gcmResponse = bundle.getParcelable(INTENT_EXTRAS.GCM_RESPONSE);
		}
	}

	/**
	 * method to initialize the UI
	 */
	private void initViewControls() {
		declineButton = (Button) findViewById(R.id.activity_incoming_call_pop_up_decline_btn);
//		declineButton.setTypeface(Utils.getTypeface(FontFace.AlteHaas, this));
		acceptButton = (Button) findViewById(R.id.activity_incoming_call_pop_up_accept_btn);
//		acceptButton.setTypeface(Utils.getTypeface(FontFace.AlteHaas, this));
		friendNameTextView = (TextView) findViewById(R.id.activity_incoming_call_pop_up_frnd_name_texview);

		// introduceEditText = (EditText)
		// findViewById(R.id.activity_incoming_call_pop_up_introduce_mssg_edit_text);

		// usernameTextView = (TextView)
		// findViewById(R.id.activity_incoming_call_pop_up_user_name_textview);
		// placeTextView = (TextView)
		// findViewById(R.id.activity_incoming_call_pop_up_place_textview);
		// userAgaeTextView = (TextView)
		// findViewById(R.id.activity_incoming_call_pop_up_user_age_textview);
		// relationTextView = (TextView)
		// findViewById(R.id.activity_incoming_call_pop_up_user_relationship_status_textview);
		userImageView = (ImageView) findViewById(R.id.activity_incoming_call_pop_up_user_imageview);
		declineButton.setOnClickListener(this);
		acceptButton.setOnClickListener(this);
		// tag1_textvw = (TextView) findViewById(R.id.tag1_textvw);
		// tag2_textvw = (TextView) findViewById(R.id.tag2_textvw);
		// tags_ln_lyout = (LinearLayout) findViewById(R.id.tags_ln_lyout);
		// tag1_rl_lyout = (RelativeLayout) findViewById(R.id.tag1_rl_lyout);
		// tag2_rl_lyout = (RelativeLayout) findViewById(R.id.tag2_rl_lyout);
		// tag1_imgvw = (ImageView) findViewById(R.id.tag1_imgvw);
		// tag2_imgvw = (ImageView) findViewById(R.id.tag2_imgvw);
	}

	/**
	 * method to set the on click listeners
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_incoming_call_pop_up_decline_btn:
			sendBroadcast(stopRingIntent);
			rejectCall();
			break;

		case R.id.activity_incoming_call_pop_up_accept_btn:
			sendBroadcast(stopRingIntent);
			acceptCall();
			break;

		default:
			break;
		}
	}

	/**
	 * Method to invoke the accept call api.
	 */
	private void acceptCall() {

		try {
			// TODO write sdcard log for calling flow:

			System.out.println(" accept call api calling.");

			// callLog("", "accept call ", "started");

			// showProgressBar();
			//
			// AcceptCallInput input = new AcceptCallInput(keeper.getUserId(),
			// fromUserId, callDetail.getsId(), getDeviceId(),
			// callDetail.getChId(), new PreferenceKeeper(this).getToken());
			//
			// caller.get(IncomingCallPopUpActivity.this, 1,
			// WeCam_URLS.WC_CALL_ACCEPT_URL, false, input,
			// new AcceptCallOutput(), API_NAME.callAccept, true);

			// TODO :: call accept api

			PreferenceKeeper keeper = new PreferenceKeeper(this);

			AppRestClient.getClient().sendRequest(
					AppRequestBuilder.accept("" + keeper.getUserInfo().getId(),
							"" + fromUserId, callDetail.getsId(),
							getDeviceId(), "" + chId,
							AppConstant.OPEN_TOK_API_SECRET, "1",
							new AppResponseListener<AcceptCallOutput>(
									AcceptCallOutput.class,
									IncomingCallPopUpActivity.this) {

								@Override
								public void onSuccess(
										AcceptCallOutput callOutput,
										Long serverTime) {

									if (callOutput.getCs().equalsIgnoreCase(
											"OK")) {

										Bundle bundle = new Bundle();
										bundle.putParcelable(
												INTENT_EXTRAS.CALL_DETAIL,
												callDetail);
										bundle.putString(
												INTENT_EXTRAS.FRIEND_NAME,
												gcmResponse.getMessage());
										bundle.putString(
												INTENT_EXTRAS.FRIEND_ADDRESS,
												friendAddress);
										bundle.putInt(
												INTENT_EXTRAS.FRIEND_ONLINE_STATUS,
												friendOnlineStatus);

										Intent intent = new Intent(
												IncomingCallPopUpActivity.this,
												CallActivity.class);
										intent.putExtras(bundle);
										startActivity(intent);
										finish();
									} else {
										showToast(callOutput.getCsMsg());
									}

								}

								@Override
								public void onError(ErrorObject error) {
									// TODO Auto-generated method stub
									showToast(error.getErrorMessage());
								}
							}), API_TAG);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to invoke the reject call api.
	 */
	private void rejectCall() {

		// TODO write sdcard log for calling flow:

		// callLog("", "reject call", "started");
		// showProgressBar();
		//
		// PreferenceKeeper keeper = new PreferenceKeeper(this);
		//
		// RejectCallInput input = new RejectCallInput(fromUserId,
		// keeper.getUserId(), callDetail.getChId(), callDetail.getsId(),
		// keeper.getUserId(), keeper.getToken(), getDeviceId());
		//
		// caller.get(IncomingCallPopUpActivity.this, 1,
		// WeCam_URLS.WC_CALL_REJECT_URL, false, input,
		// new AcceptCallOutput(), API_NAME.callReject, true);

		// TODO :: reject call

		PreferenceKeeper keeper = new PreferenceKeeper(this);

		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.callReject(""+keeper.getUserInfo().getId(),
						""+fromUserId, ""+keeper.getUserInfo().getId(), callDetail
								.getsId(), ""+callDetail.getChId(), getDeviceId(),
						AppConstant.OPEN_TOK_API_SECRET, "1",
						new AppResponseListener<AcceptCallOutput>(
								AcceptCallOutput.class,
								IncomingCallPopUpActivity.this) {

							@Override
							public void onSuccess(AcceptCallOutput callRejectOutput,
									Long serverTime) {
								if (callRejectOutput != null
										&& !callRejectOutput.getCs()
												.equalsIgnoreCase("OK")) {
									showToast(callRejectOutput.getCsMsg());

								}
							}

							@Override
							public void onError(ErrorObject error) {
								// TODO Auto-generated method stub
								showToast(error.getErrorMessage());
							}
						}), API_TAG);

		finish();
	}

	/**
	 * This method handles the api call response in case the response status is
	 * success.
	 */
	// @Override
	// public void apiSuccessResult(int reqId, ApiOutput output, API_NAME type)
	// {
	// super.apiSuccessResult(reqId, output, type);
	// switch (type) {
	// case callDetail:
	//
	// hideProgressBar();
	// callDetail = ((CallDetailOutput) output).getCallDetail();
	// if (callDetail.getCs().equalsIgnoreCase("OK")) {
	//
	// manageUI(callDetail);
	//
	// // TODO write sdcard log for calling flow:
	// //
	// // showToastMessage("Call Detail : Success ", true);
	// // callLog("" + callDetail.getChId(), "api/wccDtl",
	// // "Call Detail : Success ", "caller");
	//
	// } else {
	//
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Detail : Failed ", true);
	// //
	// // callLog("", "api/wccDtl", "Call Detail : Failed ", "caller");
	//
	// showSingleButtonDialog("", callDetail.getCsMsg(),
	// getString(R.string.ok),
	// IAppConstant.DialogConstant.MAKE_CALL_FAILED_RESPONSE);
	//
	// }
	//
	// break;
	//
	// case callAccept:
	// hideProgressBar();
	// AcceptCallOutput acceptCallOutput = (AcceptCallOutput) output;
	//
	// if (acceptCallOutput.getCs().equalsIgnoreCase("OK")) {
	//
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Accept : Success ", true);
	// //
	// // callLog("" + callDetail.getChId(), "api/wccAccept",
	// // acceptCallOutput.toString(), "calle");
	//
	// Bundle bundle = new Bundle();
	// bundle.putParcelable(INTENT_EXTRAS.CALL_DETAIL, callDetail);
	// bundle.putString(INTENT_EXTRAS.FRIEND_NAME, callDetail
	// .getUserProfile().getNm());
	// bundle.putString(INTENT_EXTRAS.FRIEND_ADDRESS, friendAddress);
	// bundle.putInt(INTENT_EXTRAS.FRIEND_ONLINE_STATUS,
	// friendOnlineStatus);
	// startActivity(CallActivity.class, bundle);
	// finish();
	//
	// mixpanel.track(MixPanelConstant.MAKING_CALL_ACCEPT_SUCESS, null);
	// } else {
	//
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Accept : Failed ", true);
	// //
	// // callLog("" + callDetail.getChId(), "api/wccAccept",
	// // "Call Accept : Failed ", "calle");
	//
	// showToastMessage(acceptCallOutput.getCsMsg(), false);
	//
	// mixpanel.track(MixPanelConstant.MAKING_CALL_ACCEPT_FAILURE,
	// null);
	// }
	//
	// mixpanel.track(MixPanelConstant.MAKING_CALL_ACCEPT_SUCESS, null);
	//
	// break;
	// case callReject:
	// AcceptCallOutput callRejectOutput = (AcceptCallOutput) output;
	//
	// if (callRejectOutput != null
	// && !callRejectOutput.getCs().equalsIgnoreCase("OK")) {
	// showToastMessage(callRejectOutput.getCsMsg(), false);
	//
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Reject : Success ", true);
	// //
	// // callLog("" + callDetail.getChId(), "api/wccReject",
	// // callRejectOutput.toString(), "calle");
	//
	// }
	//
	// hideProgressBar();
	//
	// startActivityWithClearTop(LoginAnimationActivity.class);
	// finish();
	// mixpanel.track(MixPanelConstant.MAKING_CALL_DECLINE_SUCESS, null);
	// break;
	//
	// default:
	// break;
	// }
	//
	// }

	/**
	 * Method to set details in the incoming call pop based on the received
	 * callDetail response
	 * 
	 * @param callDetail1
	 */
	private void manageUI(CallDetail callDetail1) {
		// UserProfile userProfile = callDetail1.getUserProfile();
		// if (userProfile == null) {
		// return;
		// } else {
		// friendOnlineStatus = userProfile.getWs();
		// String friendName = userProfile.getNm();
		// friendAddress = userProfile.getLoc();

		friendNameTextView.setText(gcmResponse.getMessage());

		// imageLoader.displayImage(userProfile.getImg(), userImageView,
		// userImageUploadOptions);

		// }

	}

	/**
	 * This method handles the api call response in case the response status is
	 * failure.
	 */
	// @Override
	// public void apiFailureResult(int reqId, ErrorObject error, API_NAME type)
	// {
	// super.apiFailureResult(reqId, error, type);
	//
	// switch (type) {
	// case callDetail:
	// hideProgressBar();
	// showSingleButtonDialog("", error.getErrorMessage(),
	// getString(R.string.ok), -1);
	//
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Detail : Failed ", true);
	// //
	// // callLog("", "api/wccDetail", "Call Detail : Failed ", "calle");
	//
	// break;
	// case callAccept:
	// hideProgressBar();
	// showToastMessage(getString(R.string.call_api_failed_msg), false);
	//
	// mixpanel.track(MixPanelConstant.MAKING_CALL_ACCEPT_FAILURE, null);
	//
	// // showToastMessage("Call Accept : Failed ", true);
	// //
	// // callLog("" + callDetail.getChId(), "api/wccAccept",
	// // "Call Accept : Failed ", "calle");
	//
	// break;
	// case callReject:
	// // TODO write sdcard log for calling flow:
	//
	// // showToastMessage("Call Reject : Failed ", true);
	// //
	// // callLog("" + callDetail.getChId(), "api/wccReject",
	// // "Call Reject : Failed ", "calle");
	//
	// hideProgressBar();
	// startActivityWithClearTop(LoginAnimationActivity.class);
	// finish();
	//
	// mixpanel.track(MixPanelConstant.MAKING_CALL_DECLINE_FAILURE, null);
	// break;
	//
	// default:
	// break;
	// }
	// }

	/**
	 * Method to handle the GCM response for the missed call.
	 */
	private void initializeGcmResponseHandler() {
		if (missCallHandler == null) {
			missCallHandler = new Handler(new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {

					GCMResponse gcmResponse = ((GCMResponse) msg.obj);

					int response = gcmResponse.getAkey();
					switch (response) {
					case 11:
					case 12:
					case 16:
						showToast(gcmResponse.getMessage());
						sendBroadcast(incomingCallPopUpScreenViewIntent);
						break;
					default:
						break;
					}
					return false;
				}

			});
		}
	}

	/**
	 * Returns the instance of miss call handler.
	 * 
	 * @return
	 */
	public static Handler getMissCallHandler() {
		return missCallHandler;
	}

	/**
	 * Unregisters the receiver when this activity destroys.
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		unregisterReceiver(finishIncomgCallPopScreenViewReceiver);
	}

	/**
	 * Takes confirmation to end the call on pressing back button from the
	 * device.
	 */
	@Override
	public void onBackPressed() {
		showDoubleButtonDialog("", getString(R.string.leave_call_confirmation),
				getString(R.string.yes), getString(R.string.no),
				AppConstant.DialogConstant.EXIT_FROM_END_CALL_SCREEN_ACTION);
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void showDoubleButtonDialog(final String title,
			final String message, final String positiveButtonText,
			final String negativeButtonText, final int type) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(true)
				.setPositiveButton(positiveButtonText,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								singleButtonDialogClicked(type);
							}
						})
				.setNegativeButton(negativeButtonText,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								negativeButtonDialogClicked(type);
								dialog.dismiss();

							}

						});
		AlertDialog alert = builder.create();
		alert.show();

	}

	protected void negativeButtonDialogClicked(int type) {
		// TODO Auto-generated method stub

	}

	protected void singleButtonDialogClicked(int type) {
		finish();

	}

	public String getDeviceId() {
		String deviceId = ((TelephonyManager) IncomingCallPopUpActivity.this
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (deviceId != null && !deviceId.equalsIgnoreCase("null")) {
			// System.out.println("imei number :: " + deviceId);
			return deviceId;
		}

		deviceId = android.os.Build.SERIAL;
		// System.out.println("serial id :: " + deviceId);
		return deviceId;

	}

	public void hideKeyBoard() {
		try {
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (inputManager.isAcceptingText()) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		hideKeyBoard();
		super.onResume();
	}
	
}

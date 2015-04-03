package com.android.zukut.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zukut.R;
import com.android.zukut.api.op.ErrorObject;
import com.android.zukut.bo.AcceptCallOutput;
import com.android.zukut.bo.CallDetail;
import com.android.zukut.bo.GCMResponse;
import com.android.zukut.bo.MakeCall;
import com.android.zukut.httpClient.AppRequestBuilder;
import com.android.zukut.httpClient.AppResponseListener;
import com.android.zukut.httpClient.AppRestClient;
import com.android.zukut.util.AppConstant;
import com.android.zukut.util.AppConstant.FontFace;
import com.android.zukut.util.AppConstant.INTENT_EXTRAS;
import com.android.zukut.util.BlueToothClass;
import com.android.zukut.util.CallType;
import com.android.zukut.util.CircleVideoRenderer;
import com.android.zukut.util.PreferenceKeeper;
import com.android.zukut.util.Utils;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

/**
 * Class to display the screen when call is accepted by the user and it is in
 * progress.
 * 
 */

public class CallActivity extends Activity implements Session.SessionListener,
		Publisher.PublisherListener, Subscriber.VideoListener, OnClickListener {

	private OnTouchListener touchListenerPublisherView;
	private OnTouchListener touchListenerSubscriberView;
	private String tag = "CallActivity";
	private static Handler callResponseHandler;
	private FrameLayout endCallFrameLayout;
	private Publisher mPublisher;
	private Subscriber mSubscriber;
	private Session mSession;
	// private WakeLock wakeLock;
	private FrameLayout publisherViewContainer;
	private FrameLayout subscriberViewContainer;
	private FrameLayout subscriberView_Inner;
	private FrameLayout subscriberView_Outer;
	private FrameLayout publisherView_Outer;
	private FrameLayout publisherView_Inner;
	private RelativeLayout subscribeViewBody;

	private int _xDelta;
	private int _yDelta;

	private int screenWidth;
	private int screenHeight;

	private float publisherInitialPosX;
	private float publisherInitialPosY;
	private static final boolean SUBSCRIBE_TO_SELF = false;

	private static final String LOGTAG = CallActivity.class.getName();
	private static final String API_TAG = null;

	private MakeCall makeCall;
	private CallDetail callDetail;
	// private WeCamApiClient caller;
	private CallType callType;
	// Replace with your generated Session ID
	public static String SESSION_ID = "";

	// Replace with your generated Token (use Project Tools or from a
	// server-side library)

	public static String TOKEN = "";

	private long callHistoryId;

	private float screenDensity;
	private boolean isMic = true, isCam = true;

	private ImageView micImageView;
	private ImageView camImageView;

	private Button flipCamButton;
	private Button volumeButton;

	private ImageView rightArrow;
	private ImageView leftArrow;

	private Intent showCallScreenViewIntent;

	private Animation moveAnimForward;
	private Animation moveAnimBackward;

	// private Button removeAdsButton;
	// private Button reportUserButton;
	private ImageView chatImageView;

	private Button downloadButton;

	private boolean isFlipCam;
	private boolean isFriendVolumeActive = true;

	private String friendName = "";
	private String friend_img = "";

	private String adSpace = "TopAdSpace";
	// private FrameLayout addBanner;

	private Intent stopRingIntent;

	// private LinearLayout headerLinearLayout;
	private FrameLayout bodyFrameLayout;

	private ArrayList<Stream> mStreams;

	private boolean isChatRunning;

	private LinearLayout callScreenLinearLayout;
	private FrameLayout chatScreenRelativeLayout;

	private LinearLayout mMessageView;
	private ScrollView mMessageScroll;

	private GestureDetector mGestureDetector = null;

	// private MyCountDownTimer countDownTimer;

	// private final long startTime = 5 * 60 * 1000;
	private final long interval = 5 * 1000;

	private TextView chatMsgCountTextView;

	private int chatMsgCount = 0;

	private Handler hideEndCallhandler;

	private boolean isCallPopUpShowing = false;

	private StringBuilder lastMessage = null;
	private BlueToothClass blueTooth;

	// Variables for chat conversation
	// private AlgoTokAdapter algoTokAdapter;
	private boolean isLoadMore = false;
	private String chatSessionId = null, chatToken = null;
	private int count = 15;
	// private long lastTimeStamp = System.currentTimeMillis();
	private long lastmsgThreadId = -1;
	private EditText mMessageEditText;
	private String firstChatMessage;

	private TextView chat_conn_status;

	private boolean isRTcChatAvailable = true;
	private boolean isCallConnected = false;

	private boolean isChatMssgPopupShown = false;

	private HideEndCallFrame hideEndCallFrameRunnable;
	private RelativeLayout outerRelativeLayout;
	private LinearLayout innerRootLayout;

	/**
	 * Initializes the activity, manages the listeners of the UI components,
	 * takes the data from the previous activity and manages wakeLock
	 */

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_call);

		mStreams = new ArrayList<Stream>();

		screenDensity = getResources().getDisplayMetrics().density;
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;

		initViewControls();

		getDataFromBundle();

		initializeGcmResponseHandler();

		manageScreenKeepOn();

		showCallScreenViewIntent = new Intent(
				AppConstant.getCallScreenViewBroadCastAction());

		stopRingIntent = new Intent(AppConstant.getStopRingBroadCastAction());

		// countDownTimer = new MyCountDownTimer(startTime, interval);

		// startCounter();
		manageBroadcast();
	}

	private void hideEndCallLayout() {
		if (hideEndCallhandler == null) {
			hideEndCallhandler = new Handler();
		}
		if (hideEndCallFrameRunnable == null) {
			hideEndCallFrameRunnable = new HideEndCallFrame();
		}
		hideEndCallhandler.removeCallbacks(hideEndCallFrameRunnable);
		hideEndCallhandler.postDelayed(hideEndCallFrameRunnable, interval);
	}

	private class HideEndCallFrame implements Runnable {
		@Override
		public void run() {
			hideCallScreenComponent();
		}
	}

	private void startAudioMode() {
		AudioManager m_audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		m_audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
		m_audioManager.setSpeakerphoneOn(true);
	}

	private void restartAudioMode() {
		AudioManager m_audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		m_audioManager.setMode(AudioManager.MODE_NORMAL);
		m_audioManager.setSpeakerphoneOn(false);
		this.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
	}

	/**
	 * Method to show animation while calling.
	 */
	private void startAnimation() {
		moveAnimForward = AnimationUtils.loadAnimation(CallActivity.this,
				R.anim.call_screen_forward_move_animation);
		moveAnimForward.setAnimationListener((AnimationListener) this);
		rightArrow.startAnimation(moveAnimForward);

		moveAnimBackward = AnimationUtils.loadAnimation(CallActivity.this,
				R.anim.call_screen_backward_move_animation);
		moveAnimBackward.setAnimationListener((AnimationListener) this);
		leftArrow.startAnimation(moveAnimBackward);
	}

	/**
	 * Receiver to show call screen View.
	 */
	private BroadcastReceiver showCallScreenViewReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				GCMResponse response = intent.getExtras().getParcelable(
						INTENT_EXTRAS.RESPOSE);
				int responseKey = response.getAkey();

				// showToastMessage(response.toString(), false);

				switch (responseKey) {
				case 5:
					if (isCallPopUpShowing) {
						hideCallPopUpScreen(response);
					} else {
						if (response != null) {
							callAccepted(response);
						}
					}
					break;
				case 6:
					callRejected(response);
					break;
				case 7:
					callEnded(response.getMessage());
					break;
				case 10:
					showToast(response.getMessage());
					CallActivity.this.finishCallScreen();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private long callConnectedTimeStamp;
	private int callScreenTipsCount;
	private MediaPlayer player;

	/**
	 * Hides the call pop up screen.
	 * 
	 * @param response
	 */

	private void hideCallPopUpScreen(GCMResponse response) {
		try {
			sendBroadcast(stopRingIntent);
			publisherViewContainer.removeAllViews();
			publisherViewContainer.addView(mPublisher.getView());
			publisherViewContainer.setVisibility(View.VISIBLE);
			if (response != null) {
				callAccepted(response);
			}
			hideOuterView();
			isCallPopUpShowing = false;
			showCallScreenComponent();
			hideEndCallLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * Method to get data from the intent.
	 */
	private void getDataFromBundle() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			friendName = bundle.getString(INTENT_EXTRAS.FRIEND_NAME);
			friend_img = bundle.getString(INTENT_EXTRAS.FRIEND_IMAGE_ID);

			MakeCall makeCall = (MakeCall) bundle
					.getParcelable(INTENT_EXTRAS.MAKE_CALL);
			if (makeCall != null) {
				callType = CallType.makeCall;
				this.makeCall = makeCall;
				// manageAdsHeaderView(makeCall.getAds());
				isCallPopUpShowing = true;
				showCallPopUpScreen();
			} else {
				callType = CallType.acceptedCall;
				callDetail = (CallDetail) bundle
						.getParcelable(INTENT_EXTRAS.CALL_DETAIL);

				// manageAdsHeaderView(callDetail.getAds());
			}
		}
		sessionConnect();
	}

	/**
	 * Method to initialize the GCM response handler.
	 */
	private void initializeGcmResponseHandler() {
		if (callResponseHandler == null) {
			callResponseHandler = new Handler(new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					GCMResponse response = (GCMResponse) msg.obj;
					showCallScreenViewIntent.putExtra(INTENT_EXTRAS.RESPOSE,
							response);
					sendBroadcast(showCallScreenViewIntent);
					return false;
				}
			});
		}
	}

	/**
	 * Method to initialize the UI
	 */
	private void initViewControls() {
		endCallFrameLayout = (FrameLayout) findViewById(R.id.layout_header_call_screen_end_call_layout);

		((Button) findViewById(R.id.end_call)).setTypeface(Utils.getTypeface(
				FontFace.AlteHaas, this));
		publisherViewContainer = (FrameLayout) findViewById(R.id.activity_call_publisherview);
		subscribeViewBody = (RelativeLayout) findViewById(R.id.activity_wecam_body_layouts);
		publisherView_Outer = (FrameLayout) findViewById(R.id.activity_call_publisherview_circullar_outer);
		publisherView_Inner = (FrameLayout) findViewById(R.id.activity_call_publisherview_inner);
		subscriberViewContainer = (FrameLayout) findViewById(R.id.activity_call_subscriberview);
		subscriberView_Inner = (FrameLayout) findViewById(R.id.activity_call_subscriberview_circullar);
		subscriberView_Outer = (FrameLayout) findViewById(R.id.activity_call_subscriberview_circullar_outer);

		outerRelativeLayout = (RelativeLayout) findViewById(R.id.activity_call_outerview);
		innerRootLayout = (LinearLayout) findViewById(R.id.activity_call_inner_layout);

		endCallFrameLayout.setOnClickListener(this);

		// addBanner = (FrameLayout) findViewById(R.id.banner);

		micImageView = (ImageView) findViewById(R.id.activity_footer_call_screen_mic_imageview);
		camImageView = (ImageView) findViewById(R.id.activity_footer_call_screen_video_imageview);
		flipCamButton = (Button) findViewById(R.id.activity_call_flip_camera_button);
		chatImageView = (ImageView) findViewById(R.id.activity_footer_call_screen_chat_imageview);
		// volumeButton = (Button)
		// findViewById(R.id.activity_call_friend_volume_control_button);
		bodyFrameLayout = (FrameLayout) findViewById(R.id.activity_call_screen_body_layout);

		callScreenLinearLayout = (LinearLayout) findViewById(R.id.activity_call_screen_layout);
		chatScreenRelativeLayout = (FrameLayout) findViewById(R.id.activity_footer_call_screen_chat_imageview_layout);

		chatMsgCountTextView = (TextView) findViewById(R.id.activity_footer_call_screen_chat_msg_count_textview);

		micImageView.setOnClickListener(this);
		camImageView.setOnClickListener(this);
		flipCamButton.setOnClickListener(this);
		chatImageView.setOnClickListener(this);

		chatImageView.setClickable(false);
		micImageView.setClickable(false);
		camImageView.setClickable(false);
		flipCamButton.setClickable(false);

		endCallFrameLayout.setOnClickListener(this);
		volumeButton.setOnClickListener(this);
		chat_conn_status = (TextView) findViewById(R.id.chat_conn_status);
	}

	private void showPublisherAndSubscriberView() {
		if (mSubscriber != null) {
			subscriberViewContainer.removeAllViews();
			subscriberViewContainer.addView(mSubscriber.getView());
			subscriberViewContainer.setVisibility(View.VISIBLE);
		}
		if (mPublisher != null) {
			publisherViewContainer.removeAllViews();
			publisherViewContainer.addView(mPublisher.getView());
			publisherViewContainer.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Method to set the on click listeners
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_header_call_screen_end_call_layout:
			endCallStartFriendsActivity();
			break;
		// case R.id.layout_header_menu_imageview:
		// showSideMenu();
		// break;
		case R.id.activity_footer_call_screen_mic_imageview:
			isMic = !isMic;
			disableMic(isMic);
			break;
		case R.id.activity_footer_call_screen_video_imageview:
			isCam = !isCam;
			disableCam(!isCam);
			break;
		case R.id.activity_call_flip_camera_button:
			isFlipCam = !isFlipCam;
			flipCamera(isFlipCam);
			break;

		case R.id.activity_footer_call_screen_chat_imageview:
			chatMsgCount = 0;
			// showChatMssgCnfrmDialog();
			// manageChat();
			break;

		case R.id.activity_call_friend_volume_control_button:
			isFriendVolumeActive = !isFriendVolumeActive;
			muteFriendVolume(isFriendVolumeActive);
			break;
		default:
			// super.onClick(v);
			break;
		}
	}

	/**
	 * Show a message in case one of the user is using the older version of
	 * wecam (based on the flag isRtcChatEnableMssg).
	 */
	// private void showChatMssgCnfrmDialog() {
	//
	// if (!isRTcChatAvailable && !isChatMssgPopupShown) {
	//
	// isChatMssgPopupShown = true;
	//
	// String chatMessage = getString(R.string.call_screen_chat_mssg);
	// String friendName = "";
	// if (callDetail != null) {
	// try {
	// friendName = callDetail.getUserProfile().getNm().split(" ")[0];
	// } catch (Exception e) {
	// friendName = callDetail.getUserProfile().getNm();
	// }
	//
	// } else if (makeCall != null) {
	// try {
	// friendName = this.friendName.split(" ")[0];
	// } catch (Exception e) {
	// friendName = this.friendName;
	// }
	// }
	// chatMessage = chatMessage.replace("USER_NAME", friendName);
	// showSingleButtonDialog(
	// "",
	// chatMessage,
	// getString(R.string.ok),
	// IAppConstant.DialogConstant.CALL_SCREEN_WEB_RTC_CHAT_NOT_SUPPORTED);
	// }
	// }

	/**
	 * Method to manage the chat screen view.
	 */
	// private void manageChat() {
	// if (isChatRunning) {
	// isChatRunning = false;
	// hideChatScreen();
	// showCallScreenComponent();
	// } else {
	// isChatRunning = true;
	// if (mMessageEditText == null) {
	// manageChatViewControls();
	// }
	// showChatScreen();
	// hideCallScreenComponent();
	// if (chatMsgCountTextView.getVisibility() == View.VISIBLE) {
	// chatMsgCountTextView.setVisibility(View.INVISIBLE);
	// }
	// }
	// }

	/**
	 * Method to show the chat screen view.
	 */
	// private void showChatScreen() {
	// try {
	// // int screenHeight = getScreenHeight();
	//
	// callScreenLinearLayout.setVisibility(View.VISIBLE);
	// chatScreenRelativeLayout.setVisibility(View.INVISIBLE);
	//
	// // callScreenLinearLayout.getLayoutParams().height = ((screenHeight
	// // * 80) / 100);
	// // chatScreenRelativeLayout.getLayoutParams().height =
	// // ((screenHeight * 30) / 100);
	//
	// String chatScreenHeight = getString(R.string.chat_screen_height);
	// callScreenLinearLayout.getLayoutParams().height = screenHeight
	// - Integer.parseInt(chatScreenHeight) + 10;
	//
	// mMessageScroll.post(new Runnable() {
	// @Override
	// public void run() {
	// int totalHeight = mMessageView.getHeight();
	// mMessageScroll.smoothScrollTo(0, totalHeight);
	// }
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// manageSubscriberView();
	// }

	/**
	 * Method to manage the subscriber view (i.e, friend's video view), based on
	 * whether the chat screen is on or off.
	 */
	private void manageSubscriberView() {
		try {
			// if (isChatRunning) {
			// String publisherHeight =
			// getString(R.string.chat_screen_publisher_dimens);
			// String subscriberHeight =
			// getString(R.string.chat_screen_subscriber_dimens);
			// String chatScreenHeight = getString(R.string.chat_screen_height);
			//
			// int subscriberDimens = Integer.parseInt(subscriberHeight);
			// int publisherDimens = Integer.parseInt(publisherHeight);
			//
			// FrameLayout.LayoutParams layoutParamsPublisher = new
			// FrameLayout.LayoutParams(
			// publisherDimens, publisherDimens);
			// layoutParamsPublisher.gravity = Gravity.CENTER;
			//
			// FrameLayout.LayoutParams layoutParamsSubscriber = new
			// FrameLayout.LayoutParams(
			// subscriberDimens, subscriberDimens);
			// layoutParamsSubscriber.gravity = Gravity.CENTER;
			//
			// int positionY = screenHeight - subscriberDimens
			// - Integer.parseInt(chatScreenHeight);
			// int positionX = 5;
			//
			// publisherViewContainer.removeAllViews();
			// publisherViewContainer.setVisibility(View.GONE);
			//
			// publisherView_Outer.setVisibility(View.VISIBLE);
			//
			// subscriberViewContainer.removeAllViews();
			// subscriberViewContainer.setVisibility(View.GONE);
			//
			// subscriberView_Outer.setVisibility(View.VISIBLE);
			// subscribeViewBody
			// .setOnTouchListener(touchListenerSubscriberView);
			// subscriberView_Outer
			// .setOnTouchListener(touchListenerSubscriberView);
			// publisherView_Outer
			// .setOnTouchListener(touchListenerSubscriberView);
			//
			// ImageView im1 = new ImageView(this);
			// im1.setBackgroundResource(R.drawable.profile_bg);
			// ImageView im2 = new ImageView(this);
			// im1.setBackgroundResource(R.drawable.profile_bg);
			//
			// subscriberView_Inner.addView(mSubscriber.getView(),
			// layoutParamsSubscriber);
			// subscriberView_Outer.addView(im1, layoutParamsSubscriber);
			// DisplayMetrics metrics = new DisplayMetrics();
			// getWindowManager().getDefaultDisplay().getMetrics(metrics);
			// subscriberView_Outer.setX(positionX);
			// subscriberView_Outer.setY(positionY);
			//
			// publisherView_Inner.addView(mPublisher.getView(),
			// layoutParamsPublisher);
			// publisherView_Outer.addView(im2, layoutParamsPublisher);
			// publisherView_Outer.setX(metrics.widthPixels - subscriberDimens
			// - positionX);
			// publisherView_Outer.setY(positionY);
			//
			// } else {
			subscribeViewBody.setOnTouchListener(null);
			publisherView_Inner.removeAllViews();
			publisherView_Outer.setVisibility(View.GONE);
			int publisherHeight = (int) (screenDensity * 118);
			int publisherWidth = (int) (screenDensity * 118);
			FrameLayout.LayoutParams layoutParamsPublisher = new FrameLayout.LayoutParams(
					publisherWidth, publisherHeight);
			layoutParamsPublisher.gravity = Gravity.CENTER;
			publisherViewContainer.addView(mPublisher.getView(),
					layoutParamsPublisher);
			publisherViewContainer.setVisibility(View.VISIBLE);
			publisherViewContainer.setY(publisherInitialPosY);
			publisherViewContainer.setX(publisherInitialPosX);
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int subscriberHeight = metrics.heightPixels;
			int SubscriberWidth = metrics.widthPixels;
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					SubscriberWidth, subscriberHeight);
			layoutParams.gravity = Gravity.CENTER;
			subscriberViewContainer.setVisibility(View.VISIBLE);
			subscriberView_Inner.removeAllViews();

			subscriberViewContainer
					.addView(mSubscriber.getView(), layoutParams);

			subscriberView_Outer.setVisibility(View.GONE);

			// }
		} catch (Exception e) {
			callEnded(null);
		}
	}

	// /**
	// * Method to hide the chat screen view.
	// */
	// private void hideChatScreen() {
	// int screenHeight = getScreenHeight();
	// callScreenLinearLayout.setVisibility(View.VISIBLE);
	// chatScreenRelativeLayout.setVisibility(View.VISIBLE);
	// callScreenLinearLayout.getLayoutParams().height = screenHeight;
	// manageSubscriberView();
	// }
	//
	//
	// /**
	// * Method to get screen width
	// *
	// *
	// * @return
	// */
	// protected int getScreenHeight() {
	// DisplayMetrics metrics = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(metrics);
	//
	// return metrics.heightPixels;
	// }

	/**
	 * Shows a dialog to report the user.
	 */
	// private void showReportUserDialog() {
	// final Dialog dialog = new Dialog(this);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//
	// dialog.setContentView(R.layout.layout_report_user_pop_up);
	//
	// final EditText reportEditText = (EditText) dialog
	// .findViewById(R.id.layout_report_user_editText);
	// final Button cancelButton = (Button) dialog
	// .findViewById(R.id.layout_report_user_cancel_button);
	// final Button reportButton = (Button) dialog
	// .findViewById(R.id.layout_report_user_report_button);
	//
	// reportEditText.addTextChangedListener(new TextWatcher() {
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start, int before,
	// int count) {
	// }
	//
	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count,
	// int after) {
	// }
	//
	// @Override
	// public void afterTextChanged(Editable s) {
	// if (reportEditText.getText().toString().trim().length() > 0) {
	// reportButton.setClickable(true);
	// reportButton
	// .setBackgroundResource(R.drawable.options_button_selector);
	// } else {
	// reportButton.setClickable(false);
	// reportButton
	// .setBackgroundResource(R.drawable.blue_rounded_border_unselected);
	// }
	// }
	// });
	//
	// cancelButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// dialog.dismiss();
	// }
	// });
	//
	// reportButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// String reportDesc = reportEditText.getText().toString();
	// if (reportDesc.trim().length() > 0) {
	// dialog.dismiss();
	// callreportUserApi(reportDesc);
	// }
	// }
	// });
	//
	// dialog.show();
	// }

	/**
	 * Make an API call to report the user.
	 * 
	 * @param reportDesc
	 */
	// private void callreportUserApi(String reportDesc) {
	// if (callDetail != null) {
	// ReportUserInput input = new ReportUserInput(callDetail
	// .getUserProfile().getuId(), reportDesc, keeper.getUserId(),
	// keeper.getToken());
	// caller.post(this, 1, WeCam_URLS.REPORT_USER_URL, false, input,
	// new ReportUserOutput(), API_NAME.reportUser);
	// } else if (makeCall != null) {
	// ReportUserInput input = new ReportUserInput(makeCall.getuId(),
	// reportDesc, keeper.getUserId(), keeper.getToken());
	// caller.post(this, 1, WeCam_URLS.REPORT_USER_URL, false, input,
	// new ReportUserOutput(), API_NAME.reportUser);
	// }
	// }

	/**
	 * Flips the camera to back or front based on users choice
	 * 
	 */
	private void flipCamera(boolean isFlipCam) {
		try {

			flipCamButton.setSelected(isFlipCam);

			this.getPublisher().swapCamera();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Shows a confirmation dialog whether to end the call or not.
	 */
	// @Override
	// protected void startProfileActivity() {
	// showDoubleButtonDialog("", getString(R.string.end_call_confirmation),
	// getString(R.string.yes), getString(R.string.no),
	// IAppConstant.DialogConstant.END_CALL_START_PROFILE_ACTIVITY);
	// }

	/**
	 * Disable the camera and stops the users video from streaming.
	 */
	private void disableCam(boolean b) {
		try {

			this.getPublisher().setPublishVideo(b);

			if (b) {

				camImageView

				.setBackgroundResource(R.drawable.call_activity_cam_selector);

			} else {

				camImageView

				.setBackgroundResource(R.drawable.video_record_icon_unselected);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Stops the users voice from reaching the other end.
	 */
	private void disableMic(boolean b) {
		try {

			this.getPublisher().setPublishAudio(b);

			if (b) {

				micImageView

				.setBackgroundResource(R.drawable.call_activity_mic_selector);

			} else {

				micImageView

				.setBackgroundResource(R.drawable.voice_record_icon_unselected);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * For muting the incoming sound from the friends end.
	 */
	private void muteFriendVolume(boolean isFriendVolumeActive) {
		if (this.getSubscriber() != null) {

			try {

				this.getSubscriber().setSubscribeToAudio(isFriendVolumeActive);

				if (isFriendVolumeActive) {

					volumeButton.setBackgroundResource(R.drawable.sound_icon);

				} else {

					volumeButton

					.setBackgroundResource(R.drawable.sound_icon_disabled);

				}
			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	/**
	 * This method makes an API call for ending the call, and then takes the
	 * user to Friends activity.
	 */
	private void endCallStartFriendsActivity() {
		// TODO :: end call
		PreferenceKeeper keeper = new PreferenceKeeper(this);
		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.callEnd(SESSION_ID, ""
						+ keeper.getUserInfo().getId(), "" + callHistoryId, ""
						+ keeper.getUserInfo().getId(),
						AppConstant.OPEN_TOK_API_SECRET,
						new AppResponseListener<String>(String.class,
								CallActivity.this) {

							@Override
							public void onSuccess(String t, Long serverTime) {
								finish();

							}

							@Override
							public void onError(ErrorObject error) {
								finish();
							}
						}), API_TAG);

		finish();

	}

	//
	// /**
	// * This method makes an API call for ending the call, and then takes the
	// * user to User Profile activity.
	// */
	// private void endCallStartUserProfileActivity() {
	// PreferenceKeeper keeper = new PreferenceKeeper(this);
	// CallEndedInput input = new CallEndedInput(keeper.getUserId(),
	// callHistoryId, SESSION_ID, keeper.getUserId(),
	// keeper.getToken());
	//
	// caller.get(CallActivity.this, 2, WeCam_URLS.WC_CALL_END_URL, false,
	// input, new CallEndedOutput(), API_NAME.callEnd, true);
	//
	// }

	/**
	 * Returns the instance of call responding handler.
	 * 
	 * @return
	 */
	public static Handler getCallResponseHandler() {
		return callResponseHandler;
	}

	/**
	 * Starts the Flurry agent session and prepares it for handling the ads.
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * Ends the Flurry agent session, removes the add and releases the wake
	 * lock.
	 */
	@Override
	public void onStop() {
		super.onStop();
		/*
		 * if (mSession != null) { mSession.disconnect(); } if
		 * (wakeLock.isHeld()) { wakeLock.release(); } endChat();
		 * FlurryAds.removeAd(this, adSpace, addBanner);
		 * FlurryAgent.onEndSession(this); sendBroadcast(stopRingIntent);
		 * finishCallScreen();
		 */
	}

	/**
	 * Acquires the wakeLock
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (!isChatRunning) {
			showPublisherAndSubscriberView();
			showCallScreenComponent();
			hideEndCallLayout();
		}
		// if (!wakeLock.isHeld()) {
		// wakeLock.acquire();
		// }

	}

	/**
	 * Releases the wake lock.
	 */
	@Override
	protected void onPause() {
		super.onPause();

		// if (wakeLock.isHeld()) {
		// wakeLock.release();
		// }

	}

	/**
	 * Connects the openTok session, initializes the bluetooth handling and
	 * initializes the algoTokAdapter.
	 */
	private void sessionConnect() {

		System.out.println("*************************************************");

		if (callType == CallType.acceptedCall) {
			SESSION_ID = callDetail.getsId();
			TOKEN = callDetail.gettId();
			callHistoryId = callDetail.getChId();

			if (callDetail.getHvCht() == 1) {
				isRTcChatAvailable = true;
			} else {
				isRTcChatAvailable = false;
			}

		} else if (callType == CallType.makeCall) {
			SESSION_ID = makeCall.getsId();
			TOKEN = makeCall.gettId();
			callHistoryId = makeCall.getChId();

			if (makeCall.getHvCht() == 1) {
				isRTcChatAvailable = true;
			} else {
				isRTcChatAvailable = false;
			}
		}

		Log.i(tag, "** SESSION_ID ** " + SESSION_ID + "** TOKEN ** " + TOKEN);

		// mSession = new Session(CallActivity.this, SESSION_ID,
		// CallActivity.this);
		// mSession.connect(IAppConstant.OPEN_TOK_API_KEY, TOKEN);

		mSession = new Session(CallActivity.this, AppConstant.OPEN_TOK_API_KEY,
				SESSION_ID);
		mSession.setSessionListener(this);
		mSession.connect(TOKEN);

		Log.i(tag, "Call session Connect Time Stamp :: "
				+ Calendar.getInstance().getTimeInMillis());

		System.out.println("*************************************************");

		// BlueToothClass blueTooth = new BlueToothClass();
		blueTooth = new BlueToothClass();
		blueTooth.setContext(this);
		if (blueTooth.obtainProxy()) {
			Log.e(tag, "blueTooth proxy obtained");
		}
		if (blueTooth.isAvailable()) {
			Log.e(tag, "blueTooth isAvailable");
			blueTooth.startVoiceRecognition();
		}

		/************************** Connection for AlgoChat ***************************************/

		if (callType == CallType.acceptedCall) {
			lastmsgThreadId = callDetail.getChtId();
			chatToken = callDetail.getRtcTid();
			chatSessionId = callDetail.getRtcSid();
			// initiateAlgoTokTextChat();

		} else if (callType == CallType.makeCall) {
			lastmsgThreadId = makeCall.getChtId();
			chatToken = makeCall.getRtcTid();
			chatSessionId = makeCall.getRtcSid();
			// makeActiveRoomsApi(makeCall.getuId());
		}

		if (isRTcChatAvailable) {
			// initiateAlgoTokTextChat();
		}

	}

	/************************ End AlgoChat ************************************/

	/**
	 * Returns the publisher view.
	 * 
	 * @return
	 */
	public Publisher getPublisher() {
		return mPublisher;
	}

	/**
	 * Returns the subscriber view.
	 */
	public Subscriber getSubscriber() {
		return mSubscriber;
	}

	/**
	 * Returns the active session.
	 * 
	 */
	public Session getSession() {
		return mSession;
	}

	/**
	 * Returns the container of publisher
	 * 
	 */
	public FrameLayout getPublisherView() {
		return publisherViewContainer;
	}

	/**
	 * Returns the container of subscriber
	 */
	public FrameLayout getSubscriberView() {
		return subscriberViewContainer;
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

	// /**
	// * Handles the click events of the pop ups based on the IDs.
	// */
	// @Override
	// protected void singleButtonDialogClicked(int id) {
	// switch (id) {
	// case IAppConstant.DialogConstant.END_CALL_SCREEN_ACTION:
	// endCallStartFriendsActivity();
	// break;
	// case IAppConstant.DialogConstant.END_CALL_START_PROFILE_ACTIVITY:
	// endCallStartUserProfileActivity();
	// break;
	// case IAppConstant.DialogConstant.CALL_SCREEN_WEB_RTC_CHAT_NOT_SUPPORTED:
	//
	// break;
	// // case IAppConstant.DialogConstant.PURCHASE_DIALOG:
	// // onPurchaseClicked();
	// }
	// }

	/**
	 * This method handles the api call response in case the response status is
	 * success.
	 */
	// @Override
	// public void apiSuccessResult(int reqId, ApiOutput output, API_NAME type)
	// {
	// super.apiSuccessResult(reqId, output, type);
	// switch (type) {
	// case callEnd:
	// switch (reqId) {
	// case 1:
	// finishCallScreen();
	// // finish();
	// break;
	// case 2:
	// finishCallScreen();
	// // finish();
	// break;
	// }
	//
	// mixpanel.track(
	//
	// MixPanelConstant.CALL_SCREEN_END_CALL_FROM_ACTIVE_CALL_SUCESS,
	//
	// null);
	//
	// sendCallLength();
	//
	// break;
	// case reportUser:
	// showSingleButtonDialog("", getString(R.string.report_user_message),
	// getString(R.string.ok), -1);
	// mixpanel.track(MixPanelConstant.CALL_SCREEN_REPORT_USER_SUCESS,
	//
	// null);
	//
	// mixpanel.track(MixPanelConstant.CALL_SCREEN_REPORT_USER_SUCESS,
	// null);
	//
	// break;
	//
	// case callReminder:
	// // This toast is for the testing purpose only
	// // showToastMessage("Call reminder", false);
	//
	// break;
	// // case adsSubscription:
	// // showToastMessage("Ads Removed.", false);
	// // break;
	//
	// // case getActiveRoom:
	// // GetActivteRoomOutput activeRoomOutput = (GetActivteRoomOutput)
	// // output;
	// // if (activeRoomOutput != null) {
	// // manageGetActiveRoomResponse(activeRoomOutput);
	// // }
	//
	// // case chatMakeCall:
	// // MakeCallOutput makeCallOutput = (MakeCallOutput) output;
	// // manageChatMakeCallResponse(makeCallOutput.getMakeCall());
	// //
	// // break;
	// default:
	// break;
	// }
	//
	// }

	private void sendCallLength() {
		long callLengthInSec = (Calendar.getInstance().getTimeInMillis() - callConnectedTimeStamp) / 1000;

		JSONObject object = new JSONObject();

		try {

			object.put("callLength", callLengthInSec);

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	/**
	 * This method handles the api call response in case the response status is
	 * failure.
	 */
	// @Override
	// public void apiFailureResult(int reqId, ErrorObject error, API_NAME type)
	// {
	// super.apiFailureResult(reqId, error, type);
	// switch (type) {
	// case callEnd:
	// switch (reqId) {
	// case 1:
	// finishCallScreen();
	// // startActivityWithClearTop(FriendsActivity.class);
	// // finish();
	// // showToastMessage("Call could not end.", true);
	// break;
	// case 2:
	// finishCallScreen();
	// // startActivityWithClearTop(UserProfileActivity.class);
	// // finish();
	// // showToastMessage("Call could not end.", true);
	// break;
	// }
	// mixpanel.track(
	//
	// MixPanelConstant.CALL_SCREEN_END_CALL_FROM_ACTIVE_CALL_FAILURE,
	//
	// null);
	//
	// break;
	// case reportUser:
	// showToastMessage(getString(R.string.report_user_failure_message),
	// false);
	// mixpanel.track(MixPanelConstant.CALL_SCREEN_REPORT_USER_FAILURE,
	//
	// null);
	//
	// break;
	// // case adsSubscription:
	// // showToastMessage(getString(R.string.ads_remove_failure_message),
	// // false);
	// // break;
	// default:
	// break;
	// }
	// }

	/**
	 * Manages the call pop up screen controls.
	 */
	protected void manageCallPopUpScreenControls() {

		TextView callingtextTextView = (TextView) findViewById(R.id.activity_call_pop_up_calling_texview);
		final Button endCallButton = (Button) findViewById(R.id.activity_call_pop_up_end_call_button);
		endCallButton.setTypeface(Utils.getTypeface(FontFace.AlteHaas, this));
		final Button makeCallButton = (Button) findViewById(R.id.activity_call_pop_up_make_call_button);
		makeCallButton.setTypeface(Utils.getTypeface(FontFace.AlteHaas, this));
		// final RelativeLayout
		// messge_layout=(RelativeLayout)findViewById(R.id.activity_call_pop_up_status_layout);
		ImageView selfImageView = (ImageView) findViewById(R.id.activity_call_pop_up_self_imageview);
		ImageView otherImageView = (ImageView) findViewById(R.id.activity_call_pop_up_friend_imageview);
		rightArrow = (ImageView) findViewById(R.id.activity_call_pop_up_right_arrow_imageview);
		leftArrow = (ImageView) findViewById(R.id.activity_call_pop_up_left_arrow_imageview);

		/**
		 * @author abhilash changes made to ensure user profile is always shown
		 *         in background
		 */
		RelativeLayout callPopUpRelativeLayout = (RelativeLayout) findViewById(R.id.activity_call_pop_up_root_layout);

		// callPopUpRelativeLayout.setBackgroundDrawable(friendProfileBitmap);
		callPopUpRelativeLayout.setBackgroundColor(Color.WHITE);
		publisherViewContainer.removeAllViews();
		publisherViewContainer.setVisibility(View.GONE);
		endCallButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				rejectCall();
				// recycle bitmap.
			}
		});

		callingtextTextView.setText(getString(R.string.txt_calling) + " "
				+ friendName);
		// imageLoader.displayImage(friend_img, otherImageView,
		// userImageUploadOptions);
		// imageLoader.displayImage(keeper.getUserImageId(), selfImageView,
		// userImageUploadOptions);

		endCallButton.setVisibility(View.VISIBLE);
		makeCallButton.setVisibility(View.GONE);
		// messge_layout.setVisibility(View.INVISIBLE);o
		startAnimation();
	}

	/**
	 * Makes a reject call API and removes the call pop up.
	 */
	public void rejectCall() {

		// TODO :: write code here for reject call
		//
		// PreferenceKeeper keeper = new PreferenceKeeper(this);
		//
		// RejectCallInput input = new RejectCallInput(keeper.getUserId(),
		// makeCall.getuId(), makeCall.getChId(), makeCall.getsId(),
		// keeper.getUserId(), keeper.getToken(), getDeviceId());
		//
		// caller.get(CallActivity.this, 1, WeCam_URLS.WC_CALL_REJECT_URL,
		// false,
		// input, new AcceptCallOutput(), API_NAME.callReject, true);
		//
		// finishCallScreen();

		PreferenceKeeper keeper = new PreferenceKeeper(this);

		AppRestClient.getClient().sendRequest(
				AppRequestBuilder.callReject("" + keeper.getUserInfo().getId(),
						"" + makeCall.getuId(), ""
								+ keeper.getUserInfo().getId(), makeCall
								.getsId(), "" + callDetail.getChId(),
						getDeviceId(), AppConstant.OPEN_TOK_API_SECRET, "1",
						new AppResponseListener<AcceptCallOutput>(
								AcceptCallOutput.class, CallActivity.this) {

							@Override
							public void onSuccess(
									AcceptCallOutput callRejectOutput,
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
	 * Unregisters the show_call_screen_receiver.
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(showCallScreenViewReceiver);
	}

	/**
	 * Ends the call.
	 */
	private void callEnded(String message) {

		CallActivity.this.finishCallScreen();
	}

	/**
	 * Shows a call rejected toast when the call is rejected from the friends
	 * end.
	 */
	private void callRejected(GCMResponse gcmResponse) {
		showToast(gcmResponse.getMessage());
		// startActivityWithClearTop(FriendsActivity.class);
		CallActivity.this.finishCallScreen();
	}

	/**
	 * Shows a call accepted toast when the call is accepted by the friends
	 * 
	 */
	private void callAccepted(GCMResponse gcmResponse) {
		showToast(gcmResponse.getMessage());
	}

	/**
	 * Lobby Call back method to notify that the session has connected. The
	 * publisher view is shown in a circle.
	 */
	@Override
	public void onConnected(Session arg0) {
		// Log.i("WeCAM CALL", "session connected");

		// TODO write sdcard log for calling flow:

		// callLog("" + callHistoryId, " connected ", "connected(Session arg0)",
		// 0);

		Log.i("", " open tok connected(Session arg0) method called.");

		showCallScreenComponent();
		hideEndCallLayout();
		chatImageView.setClickable(true);
		micImageView.setClickable(true);
		camImageView.setClickable(true);
		flipCamButton.setClickable(true);

		// showToastMessage("session connected", false);

		Log.i(LOGTAG, "Connected to the session.");

		if (mPublisher == null) {
			mPublisher = new Publisher(CallActivity.this, "publisher");
			mPublisher.setPublisherListener(this);
			CircleVideoRenderer circularVideorender = new CircleVideoRenderer(
					this);
			mPublisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_FIT,
					BaseVideoRenderer.STYLE_VIDEO_SCALE);
			int publisherHeight = (int) (screenDensity * 118);
			int publisherWidth = (int) (screenDensity * 118);

			mPublisher.setRenderer(circularVideorender);
			publisherInitialPosX = screenWidth - publisherWidth - 5;
			publisherInitialPosY = screenHeight - publisherHeight
					- (int) (100 * screenDensity);

			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					publisherWidth, publisherHeight);
			layoutParams.gravity = Gravity.CENTER;
			publisherViewContainer.addView(mPublisher.getView(), layoutParams);

			publisherViewContainer.setX(publisherInitialPosX);
			publisherViewContainer.setY(publisherInitialPosY);

			touchListenerPublisherView = new OnTouchListener() {

				@Override
				public boolean onTouch(View view, MotionEvent event) {
					final int X = (int) event.getRawX();
					final int Y = (int) event.getRawY();
					switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
						RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view
								.getLayoutParams();

						_xDelta = X - lParams.leftMargin;
						_yDelta = Y - lParams.topMargin;
						break;
					case MotionEvent.ACTION_UP:
						break;
					case MotionEvent.ACTION_POINTER_DOWN:
						break;
					case MotionEvent.ACTION_POINTER_UP:
						break;
					case MotionEvent.ACTION_MOVE:
						RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
								.getLayoutParams();

						layoutParams.leftMargin = X - _xDelta;
						layoutParams.topMargin = Y - _yDelta;
						layoutParams.rightMargin = -250;
						layoutParams.bottomMargin = -250;
						view.setLayoutParams(layoutParams);
						break;
					}
					showCallScreenComponent();
					hideEndCallLayout();

					return true;

				}
			};

			touchListenerSubscriberView = new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// manageChat();
					return false;
				}
			};
			publisherViewContainer
					.setOnTouchListener(touchListenerPublisherView);

			mSession.publish(mPublisher);

		}

		isCallConnected = true;

		callConnectedTimeStamp = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * This method is used for subscribing a stream.o
	 * 
	 * @param stream
	 */
	private void subscribeToStream(Stream stream) {

		// TODO write sdcard log for calling flow:

		System.out
				.println(" open tok subscribeToStream(Stream stream) method called.");

		// callLog("" + callHistoryId, " subscribeToStream ",
		// "open tok subscribeToStream(Stream stream) method called.", 0);

		// manage call pop
		if (isCallPopUpShowing) {
			hideCallPopUpScreen(null);
		}

		mSubscriber = new Subscriber(CallActivity.this, stream);
		mSubscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
				BaseVideoRenderer.STYLE_VIDEO_FILL);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				getResources().getDisplayMetrics().widthPixels, getResources()
						.getDisplayMetrics().heightPixels);
		subscriberViewContainer.addView(mSubscriber.getView(), layoutParams);

		mSession.subscribe(mSubscriber);

		startAudioMode();
	}

	/**
	 * This method is used for un-subscribing a stream.
	 * 
	 * @param stream
	 */
	private void unsubscriberToStream(Stream stream) {

		// TODO write sdcard log for calling flow:

		// callLog("" + callHistoryId, " unsubscriberToStream ",
		// "open tok unsubscriberToStream(Stream stream) method called.", 0);

		mStreams.remove(stream);
		if (mSubscriber.getStream().getStreamId().equals(stream.getStreamId())) {
			subscriberViewContainer.removeView(mSubscriber.getView());
			mSubscriber = null;
			if (!mStreams.isEmpty()) {
				subscribeToStream(mStreams.get(0));
			}
		}
	}

	/**
	 * Its an openTok Lobby call back to show error in case the exception is
	 * raised.
	 */
	@Override
	public void onError(Session session, OpentokError exception) {
		// TODO write sdcard log for calling flow:

		// System.out
		// .println(" open tok error(Session session, OpentokError exception)  method called.");

		// callLog(
		// "" + callHistoryId,
		// "error(Session session, OpentokError exception) ",
		// " open tok error(Session session, OpentokError exception)  method called.",
		// 2);

		Log.i(LOGTAG, "Session exception: " + exception.getMessage());
	}

	/**
	 * Lobby Call back to notify that the stream is received.
	 */
	@Override
	public void onStreamReceived(Session session, Stream stream) {

		// TODO write sdcard log for calling flow:

		System.out
				.println(" open tok receivedStream(Session session, Stream stream)  method called.");

		// callLog(
		// "" + callHistoryId,
		// "receivedStream(Session session, Stream stream).",
		// " open tok receivedStream(Session session, Stream stream)  method called.",
		// 0);

		// showToastMessage("Show received Stream", true);

		if (!SUBSCRIBE_TO_SELF) {
			mStreams.add(stream);
			if (mSubscriber == null) {
				subscribeToStream(stream);
			}
		}
	}

	/**
	 * Lobby Call back to notify that the stream is dropped.
	 */
	@Override
	public void onStreamDropped(Session session, Stream stream) {

		// TODO write sdcard log for calling flow:

		System.out
				.println(" open tok droppedStream(Session session, Stream stream)  method called.");

		// callLog(
		// "" + callHistoryId,
		// "droppedStream(Session session, Stream stream)",
		// " open tok droppedStream(Session session, Stream stream)  method called.",
		// 0);

		// showToastMessage("Show dropped Stream", true);

		if (mSubscriber != null) {
			unsubscriberToStream(stream);
			isCallConnected = false;
		}
	}

	/**
	 * Call back to notify that publisher stream is ready.
	 */
	@Override
	public void onStreamCreated(PublisherKit publisher, Stream stream) {

		// TODO write sdcard log for calling flow:

		// System.out
		// .println(" open tok streamCreated(PublisherKit publisher, Stream stream)  method called.");

		// callLog(
		// "" + callHistoryId,
		// "streamCreated(PublisherKit publisher, Stream stream)",
		// "open tok streamCreated(PublisherKit publisher, Stream stream)  method called.",
		// 0);

		if (SUBSCRIBE_TO_SELF) {
			mStreams.add(stream);
			if (mSubscriber == null) {
				subscribeToStream(stream);
			}
		}
	}

	/**
	 * Call back to notify that the publisher stream is destroyed.
	 */
	@Override
	public void onStreamDestroyed(PublisherKit publisher, Stream stream) {

		// TODO write sdcard log for calling flow:

		System.out
				.println(" open tok streamDestroyed(PublisherKit publisher, Stream stream)  method called.");

		// callLog(
		// "" + callHistoryId,
		// "streamDestroyed(PublisherKit publisher, Stream stream)",
		// "open tok streamDestroyed(PublisherKit publisher, Stream stream)  method called.",
		// 2);

	}

	/**
	 * Call back to notify that the subscriber stream is connected.
	 */
	// @Override
	// public void connected(SubscriberKit subscriber) {
	// Log.i(LOGTAG, "Subscriber connected.");
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId, "connected(SubscriberKit subscriber)",
	// // "Subscriber connected.", 0);
	//
	// System.out
	// .println(" open tok connected(SubscriberKit subscriber)  method called.");
	// // showToastMessage("Subscriber connected.", false);
	//
	// }

	// /**
	// * Call back to notify that the subscriber stream is disconnected.
	// */
	// @Override
	// public void disconnected(SubscriberKit arg0) {
	// Log.i(LOGTAG, "Subscriber disconnected.");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId, "disconnected(SubscriberKit arg0)",
	// // "Subscriber disconnected.", 0);
	//
	// System.out
	// .println(" open tok disconnected(SubscriberKit arg0) method called.");
	//
	// // showToastMessage("Subscriber disconnected.", false);
	//
	// }

	/**
	 * Call back to notify that the error raised in subscriber streaming.
	 */
	// @Override
	// public void error(SubscriberKit subscriber, OpentokError exception) {
	// Log.i(LOGTAG, "Subscriber exception: " + exception.getMessage());
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId,
	// // "error(SubscriberKit subscriber, OpentokError exception)",
	// // "Subscriber exception: " + exception.getMessage(), 2);
	//
	// System.out
	// .println(" open tok error(SubscriberKit subscriber, OpentokError exception) method called.");
	// // showToastMessage("Subscriber exception: " + exception.getMessage(),
	// // false);
	//
	// }

	// /**
	// * Call back to notify that the publisher camera has changed.
	// */
	// @Override
	// public void changedCamera(PublisherKit publisher, int newCameraId) {
	// Log.i(LOGTAG, "The publisher changed camera.");
	// // TODO write sdcard log for calling flow:
	//
	// System.out
	// .println(" open tok changedCamera(PublisherKit publisher, int newCameraId) method called.");
	//
	// // callLog("" + callHistoryId,
	// // "changedCamera(PublisherKit publisher, int newCameraId)",
	// // "The publisher changed camera.", 0);
	//
	// // showToastMessage("The publisher changed camera.", false);
	//
	// }

	// /**
	// * Lobby Call back to notify that the publisher starts streaming.
	// */
	// @Override
	// public void addPublisher(Session session, PublisherKit publisher) {
	// Log.i(LOGTAG, "The publisher starts streaming");
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId,
	// // "addPublisher(Session session, PublisherKit publisher)",
	// // "The publisher starts streaming.", 0);
	//
	// System.out
	// .println(" open tok addPublisher(Session session, PublisherKit publisher) method called.");
	// // showToastMessage("The publisher starts streaming", false);
	// }

	// /**
	// * Lobby Call back to notify that the publisher stops streaming.
	// */
	// @Override
	// public void removePublisher(Session session, PublisherKit publisher) {
	// Log.i(LOGTAG, "The publisher stops streaming");
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId,
	// // "removePublisher(Session session, PublisherKit publisher)",
	// // "The publisher stops streaming", 0);
	//
	// System.out
	// .println(" open tok removePublisher(Session session, PublisherKit publisher) method called.");
	// // showToastMessage("The publisher stops streaming", false);
	//
	// }

	/**
	 * Call back to notify that the error raised in publisher streaming.
	 */
	@Override
	public void onError(PublisherKit publisher, OpentokError exception) {
		Log.i(LOGTAG, "Publisher exception: " + exception.getMessage());
		// TODO write sdcard log for calling flow:

		// callLog("" + callHistoryId,
		// "error(PublisherKit publisher, OpentokError exception)",
		// "Publisher exception: " + exception.getMessage(), 0);

		System.out
				.println(" open tok error(PublisherKit publisher, OpentokError exception) method called.");
		// showToastMessage("Publisher exception: " + exception.getMessage(),
		// false);

	}

	// /**
	// * Lobby Call back to notify that the connection has created.
	// */
	// @Override
	// public void connectionCreated(Session session, Connection connection) {
	// Log.i(LOGTAG, "New client connected to the session.");
	// // TODO write sdcard log for calling flow:
	// showCallScreenComponent();
	// hideEndCallLayout();
	// chatImageView.setClickable(true);
	// micImageView.setClickable(true);
	// camImageView.setClickable(true);
	// flipCamButton.setClickable(true);
	// // callLog("" + callHistoryId,
	// // "connectionCreated(Session session, Connection connection) ",
	// // "New client connected to the session.", 0);
	//
	// System.out
	// .println(" open tok connectionCreated(Session session, Connection connection) method called.");
	// // showToastMessage("New client connected to the session.", false);
	//
	// }

	/**
	 * Lobby Call back to notify that the connection has destroyed.
	 */
	// @Override
	// public void connectionDestroyed(Session session, Connection connection) {
	//
	// Log.i(LOGTAG, "A client disconnected from the session.");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId,
	// // "connectionDestroyed(Session session, Connection connection)",
	// // "A client disconnected from the session.", 2);
	// //
	// // System.out
	// //
	// .println(" open tok connectionDestroyed(Session session, Connection connection) method called.");
	// isCallConnected = false;
	//
	// finish();
	//
	// showToastMessage("Call ended", false);
	// // showToastMessage("A client disconnected from the session.", false);
	//
	// sendCallLength();
	//
	// }

	// /**
	// * Call back to notify that subscriber video is disabled.
	// */
	// @Override
	// public void videoDisabled(SubscriberKit subscriber) {
	//
	// Log.i(LOGTAG,
	// "Video quality changed. It is disabled for the subscriber.");
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId,
	// // "videoDisabled(SubscriberKit subscriber)",
	// // "Video quality changed. It is disabled for the subscriber.", 0);
	//
	// System.out
	// .println(" open tok videoDisabled(SubscriberKit subscriber) method called.");
	// // showToastMessage(
	// // "Video quality changed. It is disabled for the subscriber.",
	// // false);
	//
	// }

	// /**
	// * Call back to notify that subscriber video data is received.
	// */
	// @Override
	// public void videoDataReceived(SubscriberKit arg0) {
	// Log.i(LOGTAG, "First frame received");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog("" + callHistoryId, "videoDataReceived(SubscriberKit arg0)",
	// // "First frame received", 0);
	//
	// System.out
	// .println(" open tok videoDataReceived(SubscriberKit arg0) method called.");
	// // showToastMessage("First frame received", false);
	//
	// }

	/**
	 * Converts dp to real pixels, according to the screen density.
	 * 
	 * @param dp
	 *            A number of density-independent pixels.
	 * @return The equivalent number of real pixels.
	 */
	private int dpToPx(int dp) {
		double screenDensity = this.getResources().getDisplayMetrics().density;
		return (int) (screenDensity * dp);
	}

	// /**
	// * Lobby Call back to notify that the chat signal has been received and
	// chat
	// * can be started.
	// */
	// @Override
	// public void onSignal(Session session, String type, String signal,
	// Connection connection) {
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog(
	// // "" + callHistoryId,
	// //
	// "onSignal(Session session, String type, String signal,Connection connection)",
	// // "First frame received", 0);
	//
	// System.out
	// .println(" open tok onSignal(Session session, String type, String signal,Connection connection) method called.");
	//
	// if ("wecam-chat".equals(type)) {
	// if (!isChatRunning) {
	// chatMsgCountTextView.setVisibility(View.VISIBLE);
	// showCallScreenComponent();
	// chatMsgCount++;
	// chatMsgCountTextView.setText("" + chatMsgCount);
	//
	// // play notification sound.
	// playNotificationSound();
	// }
	//
	// try {
	// JSONObject jsonObject = new JSONObject(signal);
	//
	// String userName = jsonObject.getString("sender");
	//
	// String message = jsonObject.getString("message");
	// // Spanned userNameInSpanned=
	// // Html.fromHtml("<b>"+userName+"</b>");
	// presentText(userName, message);
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	// /**
	// * Lobby Call back to notify that the changed stream has audio.
	// */
	// @Override
	// public void streamChangeHasAudio(Session session, Stream stream,
	// int audioEnabled) {
	// Log.i(LOGTAG, "Stream audio changed");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog(
	// // "" + callHistoryId,
	// //
	// "streamChangeHasAudio(Session session, Stream stream, int audioEnabled)",
	// // "Stream audio changed", 0);
	//
	// System.out
	// .println(" open tok streamChangeHasAudio(Session session, Stream stream, int audioEnabled) method called.");
	//
	// // showToastMessage("Stream audio changed", false);
	// }

	// /**
	// * Lobby Call back to notify that the changed stream has video.
	// */
	// @Override
	// public void streamChangeHasVideo(Session session, Stream stream,
	// int videoEnabled) {
	// Log.i(LOGTAG, "Stream video changed");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog(
	// // "" + callHistoryId,
	// //
	// "streamChangeHasVideo(Session session, Stream stream, int videoEnabled)",
	// // "Stream video changed", 0);
	//
	// System.out
	// .println(" open tok streamChangeHasVideo(Session session, Stream stream, int videoEnabled) method called.");
	//
	// // showToastMessage("Stream video changed", false);
	//
	// }

	// /**
	// * Lobby Call back to get the changed stream video dimensions.
	// */
	// @Override
	// public void streamChangeVideoDimensions(Session session, Stream stream,
	// int width, int height) {
	// // Log.i(LOGTAG, "Stream video dimensions changed");
	//
	// // TODO write sdcard log for calling flow:
	//
	// // callLog(
	// // "" + callHistoryId,
	// //
	// "streamChangeVideoDimensions(Session session, Stream stream, int width, int height)",
	// // "Stream video dimensions changed", 0);
	//
	// System.out
	// .println(" open tok streamChangeVideoDimensions(Session session, Stream stream, int width, int height)method called.");
	//
	// // showToastMessage("Stream video dimensions changed", false);
	//
	// }

	// *********************************************************************

	/**
	 * Lobby Call back to notify that the session is disconnected.
	 */
	@Override
	public void onDisconnected(Session arg0) {
		// TODO Auto-generated method stub

		// TODO write sdcard log for calling flow:

		System.out
				.println(" open tok disconnected(Session arg0) method called.");

		isCallConnected = false;

		finish();

		showToastMessage("Call ended", false);
		// showToastMessage("A client disconnected from the session.", false);

		sendCallLength();

		restartAudioMode();
	}

	public void showToastMessage(final String message, final boolean forLongTime) {

		Toast toast;

		if (forLongTime) {
			toast = Toast.makeText(this, message, Toast.LENGTH_LONG);

		} else {
			toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	/**
	 * Method to initialize and handle the listeners of the chat view controls.
	 */
	// public void manageChatViewControls() {
	//
	// try {
	//
	// mMessageEditText = (EditText) findViewById(R.id.message);
	//
	// final Button mssgSendButton = (Button)
	// findViewById(R.id.sendMessageButton);
	//
	// downloadButton = (Button) findViewById(R.id.download_btn);
	//
	// downloadButton.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// hideSoftKeyboard(mMessageEditText);
	// manageChat();
	//
	// }
	// });
	//
	// mssgSendButton.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // sendChatMessage(mMessageEditText.getText().toString());
	// // mMessageEditText.setText("");
	// //
	// // mixpanel.track(
	// // MixPanelConstant.CALL_SCREEN_TEXT_CHAT_USED_SUCESS,
	// // null);
	// }
	// });
	//
	// mssgSendButton.setClickable(false);
	// mssgSendButton.setAlpha(0.5f);
	//
	// mMessageEditText.addTextChangedListener(new TextWatcher() {
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start,
	// int before, int count) {
	// }
	//
	// @Override
	// public void beforeTextChanged(CharSequence s, int start,
	// int count, int after) {
	// }
	//
	// @Override
	// public void afterTextChanged(Editable s) {
	// if (mMessageEditText.getText().toString().trim().length() >= 1) {
	// mssgSendButton.setClickable(true);
	// mssgSendButton.setAlpha(1.0f);
	//
	// // Intent intent = new Intent(
	// // IAppConstant.BroadcastAction.ALGOTOK_SEND_TYPING_START_ACTION);
	// //
	// // sendBroadcast(intent);
	//
	// } else {
	// mssgSendButton.setClickable(false);
	// mssgSendButton.setAlpha(0.5f);
	//
	// // Intent intent = new Intent(
	// // IAppConstant.BroadcastAction.ALGOTOK_SEND_TYPING_STOP_ACTION);
	// //
	// // sendBroadcast(intent);
	// }
	// }
	// });
	//
	// mMessageEditText
	// .setOnEditorActionListener(new OnEditorActionListener() {
	//
	// @Override
	// public boolean onEditorAction(TextView v, int actionId,
	// KeyEvent event) {
	// sendChatMessage(mMessageEditText.getText()
	// .toString());
	// mMessageEditText.setText("");
	// return false;
	// }
	// });
	//
	// setMessageView((LinearLayout) findViewById(R.id.messageView),
	// (ScrollView) findViewById(R.id.scroller));
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * Method to set the text in the message view
	 */
	public void setMessageView(LinearLayout et, ScrollView scroller) {
		this.mMessageView = et;
		this.mMessageScroll = scroller;
	}

	/**
	 * Lobby call back to notify that the chat stream is received
	 * 
	 */
	public void onReceivedStream(Session session, Stream stream) {
		// presentText(null, "\n" + " User " + " has joined the chat");
	}

	/**
	 * Lobby call back to notify that the chat stream has been dropped
	 * 
	 */
	public void onDroppedStream(Session session, Stream stream) {
		// presentText(null, "\n" + " User " + " has left the chat");
	}

	/**
	 * method to add chat message in the message view.
	 * 
	 * @param message
	 */
	// private void presentText(String userName, String message) {
	//
	// LinearLayout inCallChat_TextView;
	// if (userName != null) {
	// inCallChat_TextView = (LinearLayout) getLayoutInflater().inflate(
	// R.layout.incallchat_textview, null);
	// ((TextView) inCallChat_TextView.findViewById(R.id.username))
	// .setText(userName);
	// ((TextView) inCallChat_TextView.findViewById(R.id.message))
	// .setText(message);
	// } else {
	// inCallChat_TextView = (LinearLayout) getLayoutInflater().inflate(
	// R.layout.incallchat_textview, null);
	// ((TextView) inCallChat_TextView.findViewById(R.id.username))
	// .setVisibility(View.GONE);
	// ((TextView) inCallChat_TextView.findViewById(R.id.space))
	// .setVisibility(View.GONE);
	// ((TextView) inCallChat_TextView.findViewById(R.id.message))
	// .setText(message);
	// ((TextView) inCallChat_TextView.findViewById(R.id.message))
	// .setGravity(Gravity.CENTER_HORIZONTAL);
	// }
	//
	// mMessageView.addView(inCallChat_TextView);
	// mMessageScroll.post(new Runnable() {
	// @Override
	// public void run() {
	// int totalHeight = mMessageView.getHeight();
	// mMessageScroll.smoothScrollTo(0, totalHeight);
	// }
	// });
	// }

	/**
	 * Method to send the chat message based on whether the webRtcChat available
	 * to the user or not Note: For old versions webRtcChat is not available
	 * 
	 */
	// public void sendChatMessage(String message) {
	//
	// if (message.trim().length() > 0) {
	//
	// if (isRTcChatAvailable) {
	// sendNewChatMessage(message.trim());
	//
	// } else {
	// // send chat when message length is greater.
	// JSONObject jsonObject = new JSONObject();
	//
	// // fetch first name from complete name.
	// String firstName = keeper.getUserName();
	// try {
	// firstName = keeper.getUserName().split(" ")[0];
	// } catch (Exception e) {
	// e.printStackTrace();
	// firstName = keeper.getUserName();
	// }
	//
	// try {
	// jsonObject.put("sender", firstName);
	// jsonObject.put("message", message);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// mSession.sendSignal("wecam-chat", jsonObject.toString());
	// }
	// }
	//
	// }

	/**
	 * Method to handle the touch event on the screen to hide/show the call
	 * screen components.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(this,
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							// show Call screen component
							if (!isChatRunning) {
								showCallScreenComponent();
								hideEndCallLayout();
							}
							return super.onSingleTapUp(e);
						}

					});
		}
		mGestureDetector.onTouchEvent(ev);

		return super.onTouchEvent(ev);
	}

	/**
	 * Method to hide the call screen component.
	 */
	private void hideCallScreenComponent() {
		endCallFrameLayout.setVisibility(View.INVISIBLE);
		micImageView.setVisibility(View.INVISIBLE);
		camImageView.setVisibility(View.INVISIBLE);
		flipCamButton.setVisibility(View.INVISIBLE);
		chatImageView.setVisibility(View.INVISIBLE);
		// reportUserButton.setVisibility(View.INVISIBLE);
		// removeAdsButton.setVisibility(View.INVISIBLE);
		volumeButton.setVisibility(View.INVISIBLE);
		chatMsgCountTextView.setVisibility(View.INVISIBLE);
	}

	/**
	 * Method to show the call screen component.
	 */
	private void showCallScreenComponent() {
		endCallFrameLayout.setVisibility(View.VISIBLE);
		micImageView.setVisibility(View.VISIBLE);
		camImageView.setVisibility(View.VISIBLE);
		flipCamButton.setVisibility(View.VISIBLE);
		chatImageView.setVisibility(View.VISIBLE);
		// reportUserButton.setVisibility(View.VISIBLE);
		// removeAdsButton.setVisibility(View.VISIBLE);
		volumeButton.setVisibility(View.VISIBLE);
		if (chatMsgCount > 0) {
			chatMsgCountTextView.setVisibility(View.VISIBLE);
		}

	}

	// /**
	// * class to handle the timer for showing and hiding the chat screen
	// * components
	// *
	// */
	// public class MyCountDownTimer extends CountDownTimer {
	// public MyCountDownTimer(long startTime, long interval) {
	// super(startTime, interval);
	// }
	//
	// @Override
	// public void onFinish() {
	// if (countDownTimer == null) {
	// countDownTimer = new MyCountDownTimer(startTime, interval);
	// }
	// countDownTimer.start();
	// }
	//
	// @Override
	// public void onTick(long millisUntilFinished) {
	// hideCallScreenComponent();
	// }
	// }

	/**
	 * Method to finish the call screen and release the bluetooth proxy when the
	 * call ends.
	 */
	public void finishCallScreen() {

		if (mSession != null) {
			mSession.disconnect();
		}
		// if (wakeLock.isHeld()) {
		// wakeLock.release();
		// }
		// endChat();
		sendBroadcast(stopRingIntent);

		super.finish();
		// overridePendingTransition(R.anim.slide_in_left,
		// R.anim.slide_out_right);
		overridePendingTransition(0, 0);
		try {
			blueTooth.releaseProxy();
			blueTooth.stopVoiceRecognition();
			blueTooth = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/******************************** Active Call Reminder handling ***********************************/
	//
	// private Handler callReminderHandler = new Handler();
	//
	// Long timer = (long) 0;
	//
	// class ActiveCallRemider implements Runnable {
	// @Override
	// public void run() {
	// if (isCallConnected) {
	// timer += IAppConstant.CALL_REMINDER_DELAY_TIME;
	// Log.e("call reminder time", timer + "");
	// callActiveCallReminder();
	// callReminderHandler.postDelayed(new ActiveCallRemider(),
	// IAppConstant.CALL_REMINDER_DELAY_TIME);
	// }
	// }
	// }

	// private void callActiveCallReminder() {
	// ActiveCallReminderInput input = new ActiveCallReminderInput(
	// callHistoryId, keeper.getUserId(), keeper.getToken());
	// caller.get(CallActivity.this, 1, WeCam_URLS.WC_CALL_REMINDER_URL,
	// false, input, new CallEndedOutput(), API_NAME.callReminder,
	// true);
	// }

	private void manageBroadcast() {

		registerReceiver(showCallScreenViewReceiver, new IntentFilter(
				AppConstant.getCallScreenViewBroadCastAction()));
		// register Chatready receiver
		// registerReceiver(chatReadyReceiver, new IntentFilter(
		// IAppConstant.BroadcastAction.ALGOTOK_CHAT_READY));

		// // register receiveNewMsg receiver
		// registerReceiver(
		// receiveNewMsgReceiver,
		// new IntentFilter(IAppConstant
		// .getReceiveNewMessageBroadCastAction()));

	}

	// private BroadcastReceiver chatReadyReceiver = new BroadcastReceiver() {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// // sendFirstGetChatHistoryRequest();
	// }
	// };

	/**
	 * Implement broadcast receiver for receiveNewMsg
	 */
	// private BroadcastReceiver receiveNewMsgReceiver = new BroadcastReceiver()
	// {
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// ChatMessage wsMsgDetail = intent
	// .getParcelableExtra(INTENT_EXTRAS.RTC_NEW_MESSGE);
	// if (wsMsgDetail.getSent_at() != 0) {
	//
	// // for showing unseen message count.
	// if (!isChatRunning) {
	// chatMsgCountTextView.setVisibility(View.VISIBLE);
	// showCallScreenComponent();
	// chatMsgCount++;
	// chatMsgCountTextView.setText("" + chatMsgCount);
	//
	// // play notification sound.
	// playNotificationSound();
	// }
	//
	// presentText(wsMsgDetail.getSender().split(" ")[0],
	// wsMsgDetail.getMessage());
	// }
	// }
	// };

	/**
	 * send new chat message
	 * 
	 * @param message
	 * @return
	 */
	// private ChatMessage sendNewChatMessage(String message) {
	// ChatMessage wsMessageDetail = new ChatMessage();
	// wsMessageDetail.setCreated_at(System.currentTimeMillis());
	// wsMessageDetail.setSender(keeper.getUserName());
	//
	// wsMessageDetail.setFrmUid(keeper.getUserId() + "");
	// wsMessageDetail.setMessage(message);
	// wsMessageDetail.setSent_at(0);
	//
	// Intent sendMessageIntent = new Intent(
	// IAppConstant.getSendNewMessageBroadCastAction());
	// sendMessageIntent.putExtra(INTENT_EXTRAS.RTC_NEW_MESSGE,
	// wsMessageDetail);
	// sendBroadcast(sendMessageIntent);
	//
	// // try {
	// //
	// // presentText(keeper.getUserName().split(" ")[0], message);
	// //
	// // } catch (Exception e) {
	// // presentText(keeper.getUserName(), message);
	// // }
	//
	// return wsMessageDetail;
	// }

	/**
	 * create instance of algoTokAdapter and fetch history
	 */
	// private void initiateAlgoTokTextChat() {
	// //
	// chatConnTextView.setText(Html.fromHtml("<b>Connecting</b>,<i>trying to connect to chat server.....</i>"));
	// // chatConnTextView.setVisibility(View.VISIBLE);
	// isLoadMore = true;
	// chatToken = keeper.getRtcToken();
	// if (chatSessionId != null && chatToken != null) {
	// Intent startChatIntent = new Intent(
	// IAppConstant.getStartChatBroadCastAction());
	// startChatIntent.putExtra(INTENT_EXTRAS.RTC_TOKEN, chatToken);
	// startChatIntent.putExtra(INTENT_EXTRAS.RTC_SESSION, chatSessionId);
	// sendBroadcast(startChatIntent);
	//
	// }
	// }

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobicules.wecamandroid.WeCamBaseActivity#finish()
	 */
	public void finish() {

		if (mSession != null) {
			mSession.disconnect();
		}
		// if (wakeLock.isHeld()) {
		// wakeLock.release();
		// }
		sendBroadcast(stopRingIntent);
		finishCallScreen();
	}

	@Override
	public void onVideoDataReceived(SubscriberKit arg0) {
		Log.i(LOGTAG, "onVideoDisableWarningLifted");
	}

	@Override
	public void onVideoDisableWarning(SubscriberKit arg0) {
		Log.i(LOGTAG, "onVideoDisableWarningLifted");
	}

	@Override
	public void onVideoDisableWarningLifted(SubscriberKit arg0) {
		Log.i(LOGTAG, "onVideoDisableWarningLifted");

	}

	@Override
	public void onVideoDisabled(SubscriberKit arg0, String arg1) {
		Log.i(LOGTAG, "onVideoDisabled");

	}

	@Override
	public void onVideoEnabled(SubscriberKit arg0, String arg1) {
		Log.i(LOGTAG, "onVideoEnabled");
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

	public void showCallPopUpScreen() {
		if (callScreenTipsCount == 5) {
			callScreenTipsCount = 0;
		}
		inflatedOuterView(R.layout.activity_call_popup);
		ImageView view = (ImageView) findViewById(R.id.activity_call_pop_up_tips_image);
		if (callScreenTipsCount == 0) {
			view.setImageResource(R.drawable.dyk_invite);
		} else if (callScreenTipsCount == 1) {
			view.setImageResource(R.drawable.dyk_location);
		} else if (callScreenTipsCount == 2) {
			view.setImageResource(R.drawable.dyk_social);
		} else if (callScreenTipsCount == 3) {
			view.setImageResource(R.drawable.dyk_status);
		} else if (callScreenTipsCount == 4) {
			view.setImageResource(R.drawable.dyk_tags);
		}
		++callScreenTipsCount;
		manageCallPopUpScreenControls();
	}

	private void inflatedOuterView(int outerViewLayoutId) {

		// innerRootLayout.setVisibility(View.VISIBLE);
		try {
			disableLayout(false, innerRootLayout);
			View currentBodyView = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(outerViewLayoutId, null);

			outerRelativeLayout.removeAllViews();

			currentBodyView.getLayoutParams();
			currentBodyView.getLayoutParams();
			outerRelativeLayout.addView(currentBodyView,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			outerRelativeLayout.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void disableLayout(boolean isClickable, ViewGroup viewGroup) {

		try {
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				child.setEnabled(isClickable);

				// if child itself is a view group then disable its children too
				if (child instanceof ViewGroup) {
					disableLayout(isClickable, (ViewGroup) child);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method is used to hideOuterView
	 */
	protected void hideOuterView() {
		// disableLayout(true, innerRootLayout);
		// hideKeyBoard();
		outerRelativeLayout.setVisibility(View.GONE);
		innerRootLayout.setVisibility(View.VISIBLE);
		// refreshView();
	}

	/**
	 * Method is used to playRingTone.
	 */
	private void playRingTone() {
		AssetFileDescriptor afd;
		try {
			afd = getResources().openRawResourceFd(R.raw.ringtone);
			if (player == null) {
				player = new MediaPlayer();
				player.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				player.setAudioStreamType(AudioManager.STREAM_RING);

				player.prepare();
				player.setLooping(true);
				player.start();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						stopRingTone();
					}
				};
				Timer timer = new Timer();
				timer.schedule(task, AppConstant.RINGTONE_DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method is used to stopRingTone.
	 */
	private void stopRingTone() {
		if (player != null && (player.isPlaying())) {
			try {
				player.stop();
				player.reset();
				player.release();
				player = null;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	public String getDeviceId() {
		String deviceId = ((TelephonyManager) CallActivity.this
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (deviceId != null && !deviceId.equalsIgnoreCase("null")) {
			// System.out.println("imei number :: " + deviceId);
			return deviceId;
		}

		deviceId = android.os.Build.SERIAL;
		// System.out.println("serial id :: " + deviceId);
		return deviceId;

	}
}

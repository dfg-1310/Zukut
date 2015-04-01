package com.android.zukut.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.zukut.R;
import com.android.zukut.bo.User;

public class UserAdapter extends BaseAdapter {
	private Activity activity;
	private List<User> list;

	public UserAdapter(Activity activity, List<User> friends) {
		this.activity = activity;
		this.list = friends;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public User getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView userNameTextView;
		TextView mobileNumberTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.layout_appoinment_list_item, null);

			holder.mobileNumberTextView = (TextView) convertView
					.findViewById(R.id.mobilenumber_textview);

			holder.userNameTextView = (TextView) convertView
					.findViewById(R.id.username_textview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		setData(holder, list.get(position));

		// holder.friendImage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// activity.startActivity(FriendProfileActivity.class);
		// }
		// });

		return convertView;
	}

	// private static class AnimateFirstDisplayListener extends
	// SimpleImageLoadingListener {
	//
	// static final List<String> displayedImages = Collections
	// .synchronizedList(new LinkedList<String>());
	//
	// @Override
	// public void onLoadingComplete(String imageUri, View view,
	// Bitmap loadedImage) {
	// if (loadedImage != null) {
	// ImageView imageView = (ImageView) view;
	// boolean firstDisplay = !displayedImages.contains(imageUri);
	// if (firstDisplay) {
	// FadeInBitmapDisplayer.animate(imageView, 500);
	// displayedImages.add(imageUri);
	// }
	// }
	// }
	// }

	/**
	 * method to set friend's data into the list cell
	 * 
	 * @param holder
	 * @param friend
	 */
	private void setData(ViewHolder holder, User user) {
		holder.userNameTextView.setText(user.getFullName());
		holder.mobileNumberTextView.setText(user.getMobile());
	}

}

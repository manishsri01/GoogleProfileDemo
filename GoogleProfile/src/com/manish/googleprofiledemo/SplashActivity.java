package com.manish.googleprofiledemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * @author manish
 * 
 */
public class SplashActivity extends Activity {
	Context mContext = SplashActivity.this;
	AccountManager mAccountManager;
	String token;
	int serverCode;
	private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Splash screen view
		setContentView(R.layout.activity_splash);
			syncGoogleAccount();
		

	}

	private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	private AbstractGetNameTask getTask(SplashActivity activity, String email,
			String scope) {
		return new GetNameInForeground(activity, email, scope);

	}

	public void syncGoogleAccount() {
		if (isNetworkAvailable() == true) {
			String[] accountarrs = getAccountNames();
			if (accountarrs.length > 0) {
				//you can set here account for login
				getTask(SplashActivity.this, accountarrs[0], SCOPE).execute();
			} else {
				Toast.makeText(SplashActivity.this, "No Google Account Sync!",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(SplashActivity.this, "No Network Service!",
					Toast.LENGTH_SHORT).show();
		}
	}
	public boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}
}
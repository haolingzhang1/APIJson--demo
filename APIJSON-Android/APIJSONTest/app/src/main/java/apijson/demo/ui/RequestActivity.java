/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package apijson.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import apijson.demo.HttpManager;
import apijson.demo.HttpManager.OnHttpResponseListener;
import apijson.demo.R;
import apijson.demo.RequestUtil;
import apijson.demo.StringUtil;
import apijson.demo.model.BaseModel;
import apijson.demo.model.Moment;
import apijson.demo.model.Privacy;
import apijson.demo.model.User;
import zuo.biao.apijson.JSON;
import zuo.biao.apijson.JSONResponse;

import static zuo.biao.apijson.StringUtil.UTF_8;

/**请求Activity
 * 向服务器发起请求查询或操作相应数据
 * @author Lemon
 */
public class RequestActivity extends UIAutoBaseActivity implements OnHttpResponseListener {
	private static final String TAG = "RequestActivity";


	public static final String INTENT_ID = "INTENT_ID";
	public static final String INTENT_URL = "INTENT_URL";
	public static final String INTENT_METHOD = "INTENT_METHOD";
	public static final String INTENT_REQUEST = "INTENT_REQUEST";

	public static final String RESULT_ID = "RESULT_ID";
	public static final String RESULT_URL = "RESULT_URL";
	public static final String RESULT_METHOD = "RESULT_METHOD";
	public static final String RESULT_NAME = "RESULT_NAME";
	public static final String RESULT_RESPONSE = "RESULT_RESPONSE";

	/**
	 * @param context
	 * @param id
	 * @param url
	 * @param method
	 * @param request
	 * @return
	 */
	public static Intent createIntent(Context context, long id, String url, String method, JSONObject request) {
		return new Intent(context, RequestActivity.class)
		.putExtra(RequestActivity.INTENT_ID, id)
		.putExtra(RequestActivity.INTENT_URL, url)
		.putExtra(RequestActivity.INTENT_METHOD, method)
		.putExtra(RequestActivity.INTENT_REQUEST, JSON.toJSONString(request));
	}





	private Activity context;
	private boolean isAlive;

	private long id;
	private String url;
	private String method;
	private String request;

	private TextView tvRequestResult;
	private ProgressBar pbRequest;
	private EditText etRequestUrl;
	private Button btnRequestRequest;

	private String error;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//FIXME 也解决不了竖屏只显示一半 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);  // AndroidManifest 设置不兼容小米 MIX 3 Android 9.0
		setContentView(R.layout.request_activity);
		context = this;
		isAlive = true;


		id = getIntent().getLongExtra(INTENT_ID, id);
		url = getIntent().getStringExtra(INTENT_URL);
		method = getIntent().getStringExtra(INTENT_METHOD);
		request = getIntent().getStringExtra(INTENT_REQUEST);

		method = StringUtil.getTrimedString(method);
		url = StringUtil.getCorrectUrl(url);

		tvRequestResult = findViewById(R.id.tvRequestResult);
		pbRequest = findViewById(R.id.pbRequest);
		etRequestUrl = findViewById(R.id.etRequestUrl);
		btnRequestRequest = findViewById(R.id.btnRequestRequest);



		etRequestUrl.setText(StringUtil.getString(StringUtil.isNotEmpty(url, true)
				? url : "http://apijson.cn:8080/"));  // TODO 把这个ip地址改成你自己服务器的
		btnRequestRequest.setText(method);

		error = String.format(getResources().getString(R.string.request_error), StringUtil.getTrimedString(btnRequestRequest));

		request();



		btnRequestRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				request();
			}
		});
		btnRequestRequest.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				openWebSite();
				return true;
			}
		});

	}


	/**向服务器发起请求，通过 {@link #onHttpResponse(int, String, Exception)} 返回结果
	 */
	private void request() {
		setRequest();

		final String fullUrl = getUrl();

		tvRequestResult.setText("requesting...\n\n url = " + fullUrl + "\n\n request = \n" + JSON.format(request) + "\n\n\n" + error);
		pbRequest.setVisibility(View.VISIBLE);

		HttpManager.getInstance().post(fullUrl, request, this);
	}

	/**用浏览器请求，只有GET请求才能正常访问
	 */
	public void openWebSite() {
		if ("get".endsWith(method) == false) {
			Toast.makeText(context, R.string.browser_can_only_receive_get_response, Toast.LENGTH_LONG).show();
		}
		setRequest();
		String webSite = null;
		try {
			webSite = StringUtil.getNoBlankString(getUrl())
					+ URLEncoder.encode(StringUtil.getNoBlankString(request), UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (StringUtil.isNotEmpty(webSite, true) == false) {
			Log.e(TAG, "openWebSite  StringUtil.isNotEmpty(webSite, true) == false >> return;");
			return;
		}

		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webSite)));
	}




	private String getUrl() {
		return url + StringUtil.getTrimedString(btnRequestRequest) + "/";
	}


	public void setRequest() {
		url = StringUtil.getNoBlankString(etRequestUrl);
		Log.d(TAG, "setRequest  url = " + url + ";\n request = " + request);
	}



	private String resultJson;

	/*Http请求响应回调函数，返回数据在这里处理
	 */
	@Override
	public void onHttpResponse(int requestCode, final String resultJson, final Exception e) {
		this.resultJson = resultJson;
		Log.d(TAG, "onHttpResponse  resultJson = " + resultJson);
		if (e != null) {
			Log.e(TAG, "onHttpResponse e = " + e.getMessage());
		}
		JSONResponse response = new JSONResponse(resultJson);

		if (RequestUtil.isLogMethod(method)) {
			User user = response.getObject(User.class);
			name = user == null ? null : user.getName();
			Log.d(TAG, "onHttpResponse  login.equals(method) || logout.equals(method) >>  name = " + name);
		}
		else if ("post".equals(method)) {
			Moment moment = response.getObject(Moment.class);
			id = moment == null ? 0 : BaseModel.value(moment.getId());
			Log.d(TAG, "onHttpResponse  post.equals(method) >>  id = " + id);
		}
		else if ("put".equals(method)) {
			response.getJSONResponse(Moment.class.getSimpleName());
			Log.d(TAG, "onHttpResponse  put.equals(method) >>  moment = " + JSON.toJSONString(response));

		}
		else if ("delete".equals(method)) {
			response = response.getJSONResponse(Moment.class.getSimpleName());
			//			if (JSONResponse.isSuccess(response)) {//delete succeed
			id = 0;//reuse default value
			//			}
			Log.d(TAG, "onHttpResponse  delete.equals(method) >>  id = " + id
					+ "; isSucceed = " + JSONResponse.isSuccess(response));
		}
		else if ("gets".equals(method)) {
			Privacy privacy = response.getObject(Privacy.class);
			Log.d(TAG, "onHttpResponse  gets.equals(method) >>  privacy = \n" + JSON.toJSONString(privacy));
		}
		else {
			//do nothing
		}


		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isAlive) {
					pbRequest.setVisibility(View.GONE);
					Toast.makeText(context, R.string.received_result, Toast.LENGTH_SHORT).show();

					tvRequestResult.setText(e == null || JSON.isJsonCorrect(resultJson)
							? JSON.format(resultJson) : e.getMessage() + "\n\n\n" + error);
				}
			}
		});
	}



	private String name;

	@Override
	public void finish() {
		setResult(RESULT_OK, new Intent()
				.putExtra(RESULT_ID, id)
				.putExtra(RESULT_URL, url)
				.putExtra(RESULT_METHOD, method)
				.putExtra(RESULT_NAME, name)
				.putExtra(RESULT_RESPONSE, resultJson)
		);

		super.finish();
	}

	@Override
	protected void onDestroy() {
		isAlive = false;
		super.onDestroy();
	}

}

package test.cn.example.com.androidskill.login.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.login.presenter.ILoginPresenter;
import test.cn.example.com.androidskill.login.presenter.LoginPresenterImpl;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;


public class LoginActivity extends Activity implements View.OnClickListener,ILoginView{
	private EditText et_name, et_psw;
	private ProgressBar pbLogin;
	private ILoginPresenter iLoginPresenter;
	private TextView tv_check;
	private boolean isClauseChecked;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		iLoginPresenter = new LoginPresenterImpl(this);
	}


	private void initView() {
		pbLogin = (ProgressBar)findViewById(R.id.pb_login);
		pbLogin.setVisibility(View.INVISIBLE);
		et_name = (EditText) findViewById(R.id.name);
		et_psw = (EditText) findViewById(R.id.psw);
		tv_check = (TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		tv_check.setSelected(isClauseChecked);
		Button btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
		Button btnClear = (Button) findViewById(R.id.btn_clear);
		btnClear.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_check:
				isClauseChecked = !isClauseChecked;
				iLoginPresenter.checkCluaseState(isClauseChecked);
				break;
			case R.id.btn_login:
				iLoginPresenter.login(et_name.getText().toString(), et_psw.getText().toString());
				break;
			case R.id.btn_clear:
				iLoginPresenter.clear();
				break;
		}
	}

	@Override
	public void upDateCluaseState(boolean isServiceClauseChecked) {
		tv_check.setSelected(isServiceClauseChecked);
	}

	@Override
	public void clearText() {
		et_name.setText("");
		et_psw.setText("");
		if(pbLogin.getVisibility() == View.VISIBLE){
			pbLogin.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onLoginResult(boolean result, int code) {
		LogUtil.i("result="+result+"---code="+code);
		if(result){
			pbLogin.setVisibility(View.INVISIBLE);
			showToast("登录成功",LoginActivity.this);
		}
	}

	@Override
	public void onSetProgressBarVisibility(int visibility) {
			pbLogin.setVisibility(View.VISIBLE);
	}

	@Override
	public void showShortToast(String s) {
		ToastUtils.shortToast(LoginActivity.this,s);
	}

	private void showToast(String msg,Context context){
		Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		楼主这里没说完整，在activity的onDestroy等类似的回调中应该要把presenter的中对view的引用给断开。presenter.setView(null);
	}

	@Override
	public void onSuccessResultView() {

	}

	@Override
	public void onEmptyResultView() {

	}

	@Override
	public void onErrorResultView() {

	}
}

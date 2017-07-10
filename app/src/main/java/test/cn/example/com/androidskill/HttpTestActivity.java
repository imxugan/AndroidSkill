package test.cn.example.com.androidskill;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import test.cn.example.com.util.LogUtil;


public class HttpTestActivity extends Activity{

	protected static final int SUCCESS = 0;
	private BufferedReader reader;
	private InputStream inputStream;
	private TextView tv;
	
	  private EditText metURL;
	    private Button mbtnConn;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				String result = (String) msg.obj;
				tv.setText(result);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_test);
		initView();
	};

	private void initView() {
		tv = (TextView) findViewById(R.id.tv);
		metURL = (EditText)findViewById(R.id.etURL);        // 输入网址
        mbtnConn = (Button)findViewById(R.id.btnConn);      // 连接网站

//        mbtnConn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                connURL();
//            }
//        });
	}

//	 private void connURL(){
//	        URLTask urlTask = new URLTask(this);    // 实例化抽象AsyncTask
//	        urlTask.execute(metURL.getText().toString().trim());    // 调用AsyncTask，传入url参数
//	    }
	 
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_httpUrlConnection:
			new Thread(){
				public void run() {
					String result = requestHttpWithUrlConnection();
					Message msg = Message.obtain();
					msg.what = SUCCESS;
					msg.obj = result;
					mHandler.sendMessage(msg);
				};
			}.start();
			
			break;
		case R.id.btn_httpClient:
//			new Thread(){
//				public void run() {
//					String result = (String) requestHttpWithClient();
//					Message msg = Message.obtain();
//					msg.what = SUCCESS;
//					msg.obj = result;
//					mHandler.sendMessage(msg);
//				};
//			}.start();
			
			break;
		default:
			break;
		}
	}

//	private Object requestHttpWithClient() {
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet("http://www.sohu.com");
//		try {
//			HttpResponse response = client.execute(get);
//			if(response.getStatusLine().getStatusCode() == 200 ){//响应成功
//				HttpEntity entity = response.getEntity();
//				String result = EntityUtils.toString(entity, "utf-8");
//				return result;
//			}
//
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
String requestHeader = null;//请求头
	private String requestHttpWithUrlConnection() {
		HttpURLConnection conn = null;
		try {
			LogUtil.i("requestHttpWithUrlConnection");
//			URL url = new URL("http://www.163.com");
//			URL url = new URL("http://api.huahefang.com/index.php?r=index/get");
			URL url = new URL("http://api.yuchanghe.com/api/api2.php");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			getReqeustHeader(conn);
			conn.connect();
			inputStream = conn.getInputStream();

			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			//StringBufferd支持并发操作，线性安全的，适 合多线程中使用。StringBuilder不支持并发操作，线性不安全的，不适合多线程中使用
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			//获取请求头
//			requestHeader = getReqeustHeader(conn);

			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}
	
//	  在开发Android移动客户端的时候往往要使用多线程来进行操作，
	//我们通常会将耗时的操作放在单独的线程执行，避免其占用主线程而给用户带来不好的用户体验。
	//但是在子线程中无法去操作主线程（UI 线程），在子线程中操作UI线程会出现错误。
	//因此android提 供了一个类Handler来在子线程中来更新UI线程，用发消息的机制更新UI界面，呈现给用户。
	//这样就解决了子线程更新UI的问题。但是费时的任务操作 总会启动一些匿名的子线程，太多的子线程给系统带来巨大的负担，
	//随之带来一些性能问题。因此android提供了一个工具类AsyncTask，顾名思义 异步执行任务。
	//这个AsyncTask生来就是处理一些后台的比较耗时的任务，给用户带来良好用户体验的，
	//从编程的语法上显得优雅了许多，不再需要子线程 和Handler就可以完成异步操作并且刷新用户界面。
//      先大概认识下Android.os.AsyncTask类：
//
//     * android的类AsyncTask对线程间通讯进行了包装，提供了简易的编程方式来使后台线程和UI线程进行通讯：后台线程执行异步任务，并把操作结果通知UI线程。
//
//     * AsyncTask是抽象类.AsyncTask定义了三种泛型类型 Params，Progress和Result。
// * Params 启动任务执行的输入参数，比如HTTP请求的URL。
// * Progress 后台任务执行的百分比。
//  * Result 后台执行任务最终返回的结果，比如String,Integer等。
//
//     * AsyncTask的执行分为四个步骤，每一步都对应一个回调方法，开发者需要实现这些方法。
//
// * 1) 继承AsyncTask
//  * 2) 实现AsyncTask中定义的下面一个或几个方法
//     * onPreExecute(), 该方法将在执行实际的后台操作前被UI 线程调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条，或者一些控件的实例化，这个方法可以不用实现。
//     * doInBackground(Params...), 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。这里将主要负责执行那些很耗时的后台处理工作。可以调用 publishProgress方法来更新实时的任务进度。该方法是抽象方法，子类必须实现。
//    * onProgressUpdate(Progress...),在publishProgress方法被调用后，UI 线程将调用这个方法从而在界面上展示任务的进展情况，例如通过一个进度条进行展示。
//    * onPostExecute(Result), 在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用，后台的计算结果将通过该方法传递到UI 线程，并且在界面上展示给用户.
//
//    * onCancelled(),在用户取消线程操作的时候调用。在主线程中调用onCancelled()的时候调用。
//
//为了正确的使用AsyncTask类，以下是几条必须遵守的准则：
//
//  　　1) Task的实例必须在UI 线程中创建
//
//  　　2) execute方法必须在UI 线程中调用
//
//  　　3) 不要手动的调用onPreExecute(), onPostExecute(Result)，doInBackground(Params...), onProgressUpdate(Progress...)这几个方法，需要在UI线程中实例化这个task来调用。
//
//  　　4) 该task只能被执行一次，否则多次调用时将会出现异常
//
//    doInBackground方法和onPostExecute的参数必须对应，这两个参数在AsyncTask声明的泛型参数列表中指定，第一个为 doInBackground接受的参数，第二个为显示进度的参数，第第三个为doInBackground返回和onPostExecute传入的参 数。

//AsyncTask<>的参数类型由用户设定，这里设为三个String
//第一个String代表输入到任务的参数类型，也即是doInBackground()的参数类型
//第二个String代表处理过程中的参数类型，也就是doInBackground()执行过程中的产出参数类型，通过publishProgress()发消息
//传递给onProgressUpdate()一般用来更新界面
//第三个String代表任务结束的产出类型，也就是doInBackground()的返回值类型，和onPostExecute()的参数类型
	/** 继承AsyncTask的子类，下载url网页内容 */
//    class URLTask extends AsyncTask<String, Integer, String> {
//        ProgressDialog proDialog;
//
//        public URLTask(Context context) {
//            proDialog = new ProgressDialog(context, 0);
//            proDialog.setButton("cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            proDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    finish();
//                }
//            });
//            proDialog.setCancelable(true);
//            proDialog.setMax(100);
//            proDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            proDialog.show();
//        }
//
//        @Override
//        protected void onPreExecute(){
//        	//提前做一些准备工作，可以放到这个方法
//            tv.setText("heollo");      // 可以与UI控件交互
//        }
//
//        @Override
//        protected String doInBackground(String... params) {     // 在后台，下载url网页内容
//            try {
//                HttpGet get = new HttpGet(params[0]);           // url
//                HttpResponse response = new DefaultHttpClient().execute(get);
//
//                if(response.getStatusLine().getStatusCode() == 200) {       // 判断网络连接是否成功
////                  String result = EntityUtils.toString(response.getEntity(), "gb2312");   // 获取网页内容
////                  return result;
//
//                    HttpEntity entity = response.getEntity();
//                    long len = entity.getContentLength();       // 获取url网页内容总大小
//                    Log.i("ii","len="+len);
//                    InputStream is = entity.getContent();
//
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[1024];
//                    int ch = -1;
//                    int count = 0;      // 统计已下载的url网页内容大小
//                    while(is != null && (ch = is.read(buffer)) != -1 ) {
//                        bos.write(buffer, 0, ch);
//                        count += ch;
//                        Log.i("ii","count="+count);
//                        if(count > 100) {
//                        	publishProgress(count/1000);// android.os.AsyncTask.publishProgress(Integer... values)
//                        }
//
//                        Thread.sleep(100);
//                    }
//                    String result = new String(bos.toString("gb2312"));
//                    return result;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {    // 可以与UI控件交互
//            tv.setText("" + values[0]);    // 获取 publishProgress((int)ratio)的values
//            proDialog.setProgress(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {   // 可以与UI控件交互
//            tv.setText(result);
//            proDialog.dismiss();
//        }
//    }


	//读取请求头
	private void getReqeustHeader(HttpURLConnection httpUrlCon) {
//		//https://github.com/square/okhttp/blob/master/okhttp-urlconnection/src/main/java/okhttp3/internal/huc/HttpURLConnectionImpl.java#L236
//		Map<String, List<String>> requestHeaderMap = httpUrlCon.getRequestProperties();
//		LogUtil.i("requestHeaderMap==="+requestHeaderMap);
//		Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();
//		LogUtil.i("requestHeaderIterator==="+requestHeaderIterator);
//		StringBuilder sbRequestHeader = new StringBuilder();
//		LogUtil.i("requestHeaderIterator.hasNext()==="+requestHeaderIterator.hasNext());
//		while (requestHeaderIterator.hasNext()) {
//			String requestHeaderKey = requestHeaderIterator.next();
//			String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);
//			sbRequestHeader.append(requestHeaderKey);
//			sbRequestHeader.append(":");
//			sbRequestHeader.append(requestHeaderValue);
//			sbRequestHeader.append("\n");
//			LogUtil.i("getReqeustHeader");
//		}
//		return sbRequestHeader.toString();

		Map requests = httpUrlCon.getRequestProperties();
		Set req = requests.keySet();
		Iterator itreq = req.iterator();
		while(itreq.hasNext()){
			//得到每个字段名
			String field = (String)itreq.next();
			//得到每个字段名所对应的值
			System.out.println(field+":"+httpUrlCon.getRequestProperty(field));
		}
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println(" " + httpUrlCon.getRequestMethod() + " / " + " HTTP/1.1");
		System.out.println(" Host: " + httpUrlCon.getRequestProperty("Host"));
		System.out.println(" Connection: " + httpUrlCon.getRequestProperty("Connection"));
		System.out.println(" Accept: " + httpUrlCon.getRequestProperty("Accept"));
		System.out.println(" User-Agent: " + httpUrlCon.getRequestProperty("User-Agent"));
		System.out.println(" Accept-Encoding: " + httpUrlCon.getRequestProperty("Accept-Encoding"));
		System.out.println(" Accept-Language: " + httpUrlCon.getRequestProperty("Accept-Language"));
		System.out.println(" Cookie: " + httpUrlCon.getRequestProperty("Cookie"));
		System.out.println(" Connection: " + httpUrlCon.getHeaderField("Connection"));//利用另一种读取HTTP头字段
		System.out.println();
	}


 }

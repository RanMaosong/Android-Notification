# Android-Notification
<div class="article">
        
<h3><strong>什么是通知(Notification)</strong></h3>
<p>通知是一个可以在应用程序正常的用户界面之外显示给用户的消息。<br>通知发出时，它首先出现在状态栏的通知区域中，用户打开通知抽屉可查看通知详情。通知区域和通知抽屉都是用户可以随时查看的系统控制区域。</p>
<p>作为安卓用户界面的重要组成部分，通知有自己的设计指南。在Android 5.0(API level 21)中引入的 <a href="http://developer.android.com/training/material/index.html" target="_blank">Material Design</a> 的变化是特别重要的，更多信息请阅读 <a href="http://developer.android.com/design/patterns/notifications.html" target="_blank">通知设计指南</a>。</p>
<h3><strong>如何创建通知</strong></h3>
<p>随着Android系统不断升级，Notification的创建方式也随之变化，主要变化如下: </p>
<h4><strong>Android 3.0之前</strong></h4>
<p>Android 3.0 (API level 11)之前，使用<code>new Notification()</code>方式创建通知: </p>
<pre class="hljs kotlin"><code class="kotlin">NotificationManager mNotifyMgr = 
      (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
PendingIntent contentIntent = PendingIntent.getActivity(
      <span class="hljs-keyword">this</span>, <span class="hljs-number">0</span>, new Intent(<span class="hljs-keyword">this</span>, ResultActivity.<span class="hljs-keyword">class</span>), <span class="hljs-number">0</span>);

Notification notification = new Notification(icon, tickerText, <span class="hljs-keyword">when</span>);
notification.setLatestEventInfo(<span class="hljs-keyword">this</span>, title, content, contentIntent);

mNotifyMgr.notify(NOTIFICATIONS_ID, notification);</code></pre>
<h4><strong>Android 3.0 (API level 11)及更高版本</strong></h4>
<p>Android 3.0开始弃用<code>new Notification()</code>方式，改用<code>Notification.Builder()</code>来创建通知:</p>
<pre class="hljs pony"><code class="pony"><span class="hljs-type">NotificationManager</span> mNotifyMgr = 
      (<span class="hljs-type">NotificationManager</span>) getSystemService(<span class="hljs-type">NOTIFICATION_SERVICE</span>);
<span class="hljs-type">PendingIntent</span> contentIntent = <span class="hljs-type">PendingIntent</span>.getActivity(
      <span class="hljs-literal">this</span>, <span class="hljs-number">0</span>, <span class="hljs-function"><span class="hljs-keyword">new</span> <span class="hljs-title">Intent</span>(this, <span class="hljs-type">ResultActivity</span>.class), 0);

<span class="hljs-title">Notification</span> <span class="hljs-title">notification</span> = <span class="hljs-title">new</span> <span class="hljs-title">Notification</span>.<span class="hljs-title">Builder</span>(this)
            .<span class="hljs-title">setSmallIcon</span>(<span class="hljs-type">R</span>.drawable.notification_icon)
            .<span class="hljs-title">setContentTitle</span>("<span class="hljs-type">My</span> notification")
            .<span class="hljs-title">setContentText</span>("<span class="hljs-type">Hello</span> <span class="hljs-type">World</span>!")
            .<span class="hljs-title">setContentIntent</span>(contentIntent)
            .<span class="hljs-title">build</span>();<span class="hljs-comment">// getNotification()</span>

<span class="hljs-title">mNotifyMgr</span>.<span class="hljs-title">notify</span>(<span class="hljs-type">NOTIFICATIONS_ID</span>, notification);</span></code></pre>
<p>这里需要注意: "build()" 是Androdi 4.1(API level 16)加入的，用以替代<br>"getNotification()"。API level 16开始弃用"getNotification()"</p>
<h4><strong>兼容Android 3.0之前的版本</strong></h4>
<p>为了兼容<code>API level 11</code>之前的版本，<code>v4 Support Library</code>中提供了<br><code>NotificationCompat.Builder()</code>这个替代方法。它与<code>Notification.Builder()</code>类似，二者没有太大区别。 </p>
<pre class="hljs haxe"><code class="haxe">NotificationManager mNotifyMgr = 
      (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
PendingIntent contentIntent = PendingIntent.getActivity(
      <span class="hljs-built_in">this</span>, <span class="hljs-number">0</span>, <span class="hljs-keyword">new</span> <span class="hljs-type">Intent</span>(<span class="hljs-built_in">this</span>, ResultActivity.class), <span class="hljs-number">0</span>);

NotificationCompat.Builder mBuilder = 
      <span class="hljs-keyword">new</span> <span class="hljs-type">NotificationCompat</span>.Builder(<span class="hljs-built_in">this</span>)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(<span class="hljs-string">"My notification"</span>)
            .setContentText(<span class="hljs-string">"Hello World!"</span>)
            .setContentIntent(contentIntent);

mNotifyMgr.notify(NOTIFICATIONS_ID, mBuilder.build());</code></pre>
<p><em>注：除特别说明外，本文将根据 NotificationCompat.Builder() 展开解析，<br>Notification.Builder()类似。</em></p>
<h3><strong>通知基本用法</strong></h3>
<h4><strong>通知的必要属性</strong></h4>
<p>一个通知必须包含以下三项属性:</p>
<ul>
<li>小图标，对应 setSmallIcon()</li>
<li>通知标题，对应 setContentTitle()</li>
<li>详细信息，对应 setContentText()</li>
</ul>
<p>其他属性均为可选项，更多属性方法请参考<a href="http://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html" target="_blank">NotificationCompat.Builder</a>。</p>
<p>尽管其他都是可选的，但一般都会为通知添加至少一个动作(Action)，这个动作可以是跳转到Activity、启动一个Service或发送一个Broadcas等。 通过以下方式为通知添加动作:</p>
<ul>
<li>使用PendingIntent</li>
<li>通过大视图通知的 Action Button //仅支持Android 4.1 (API level 16)及更高版本，稍后会介绍</li>
</ul>
<h4><strong>创建通知</strong></h4>
<p>1、实例化一个NotificationCompat.Builder对象</p>
<pre class="hljs haxe"><code class="haxe">NotificationCompat.Builder mBuilder = 
      <span class="hljs-keyword">new</span> <span class="hljs-type">NotificationCompat</span>.Builder(<span class="hljs-built_in">this</span>)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(<span class="hljs-string">"My notification"</span>)
            .setContentText(<span class="hljs-string">"Hello World!"</span>);</code></pre>
<p>NotificationCompat.Builder自动设置的默认值:</p>
<ul>
<li>priority: PRIORITY_DEFAULT</li>
<li>when: System.currentTimeMillis() </li>
<li>audio stream: STREAM_DEFAULT</li>
</ul>
<p>2、定义并设置一个通知动作(Action)</p>
<pre class="hljs kotlin"><code class="kotlin">Intent resultIntent = new Intent(<span class="hljs-keyword">this</span>, ResultActivity.<span class="hljs-keyword">class</span>);
PendingIntent resultPendingIntent = PendingIntent.getActivity(
            <span class="hljs-keyword">this</span>, <span class="hljs-number">0</span>, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
mBuilder.setContentIntent(resultPendingIntent);</code></pre>
<p>3、生成<code>Notification</code>对象 </p>
<pre class="hljs ebnf"><code class="ebnf"><span class="hljs-attribute">Notificatioin notification</span> = mBuilder.build();</code></pre>
<p>4、使用<code>NotificationManager</code>发送通知 </p>
<pre class="hljs smali"><code class="smali">// Sets an ID for the notification<span class="hljs-built_in">
int </span>mNotificationId = 001;

// Gets an<span class="hljs-built_in"> instance </span>of the NotificationManager service
NotificationManager mNotifyMgr = 
      (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

// Builds the notification<span class="hljs-built_in"> and </span>issues it.
mNotifyMgr.notify(mNotificationId, notification);</code></pre>
<h4><strong>更新通知</strong></h4>
<p>更新通知很简单，只需再次发送相同ID的通知即可，如果之前的通知依然存在则会更新通知属性，如果之前通知不存在则重新创建。<br>示例代码:</p>
<pre class="hljs pony"><code class="pony"><span class="hljs-type">NotificationManager</span> mNotifyMgr = 
      (<span class="hljs-type">NotificationManager</span>) getSystemService(<span class="hljs-type">NOTIFICATION_SERVICE</span>);
<span class="hljs-comment">// Sets an ID for the notification, so it can be updated</span>
int notifyID = <span class="hljs-number">1</span>;
<span class="hljs-type">NotificationCompat</span>.<span class="hljs-type">Builder</span> mNotifyBuilder = 
      <span class="hljs-function"><span class="hljs-keyword">new</span> <span class="hljs-title">NotificationCompat</span>.<span class="hljs-title">Builder</span>(this)
          .<span class="hljs-title">setContentTitle</span>("<span class="hljs-type">New</span> <span class="hljs-type">Message</span>")
          .<span class="hljs-title">setContentText</span>("<span class="hljs-type">You</span>'ve received new messages.")
          .<span class="hljs-title">setSmallIcon</span>(<span class="hljs-type">R</span>.drawable.ic_notify_status);

<span class="hljs-title">int</span> <span class="hljs-title">numMessages</span> = 0;
...
    <span class="hljs-title">mNotifyBuilder</span>.<span class="hljs-title">setContentText</span>("new content text")
            .<span class="hljs-title">setNumber</span>(++numMessages);
    <span class="hljs-title">mNotifyMgr</span>.<span class="hljs-title">notify</span>(notifyID, mNotifyBuilder.build());
...</span></code></pre>
<h4><strong>取消通知</strong></h4>
<p>取消通知有如下4种方式:</p>
<ul>
<li>点击通知栏的清除按钮，会清除所有可清除的通知</li>
<li>设置了 setAutoCancel() 或 FLAG_AUTO_CANCEL的通知，点击该通知时会清除它</li>
<li>通过 NotificationManager 调用 cancel() 方法清除指定ID的通知</li>
<li>通过 NotificationManager 调用 cancelAll() 方法清除所有该应用之前发送的通知</li>
</ul>
<h3><strong>通知类型</strong></h3>
<h4><strong>大视图通知</strong></h4>
<p>通知有两种视图：普通视图和大视图。<br>普通视图:<br></p><div class="image-package">
<img src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/notifications-normalview.png" data-original-src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/notifications-normalview.png" style="cursor: zoom-in;"><br><div class="image-caption"></div>
</div><p><br>大视图:<br></p><div class="image-package">
<img src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/notifications-bigview.png" data-original-src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/notifications-bigview.png" style="cursor: zoom-in;"><br><div class="image-caption"></div>
</div>
<p>默认情况下为普通视图，可通过<code>NotificationCompat.Builder.setStyle()</code>设置大视图。 </p>
<p>注: 大视图(Big Views)由Android 4.1(API level 16)开始引入，且仅支持Android 4.1及更高版本。</p>
<h5><strong>构建大视图通知</strong></h5>
<p>以上图为例:<br>1、构建Action Button的PendingIntent </p>
<pre class="hljs kotlin"><code class="kotlin">Intent dismissIntent = new Intent(<span class="hljs-keyword">this</span>, PingService.<span class="hljs-keyword">class</span>);
dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
PendingIntent piDismiss = PendingIntent.getService(
      <span class="hljs-keyword">this</span>, <span class="hljs-number">0</span>, dismissIntent, <span class="hljs-number">0</span>);

Intent snoozeIntent = new Intent(<span class="hljs-keyword">this</span>, PingService.<span class="hljs-keyword">class</span>);
snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
PendingIntent piSnooze = 
      PendingIntent.getService(<span class="hljs-keyword">this</span>, <span class="hljs-number">0</span>, snoozeIntent, <span class="hljs-number">0</span>);</code></pre>
<p>2、构建NotificatonCompat.Builder对象</p>
<pre class="hljs armasm"><code class="armasm"><span class="hljs-symbol">NotificationCompat.Builder</span> <span class="hljs-keyword">builder </span>= 
      new NotificationCompat.<span class="hljs-keyword">Builder(this)
</span>          .setSmallIcon(R.drawable.ic_stat_notification)
          .setContentTitle(getString(R.<span class="hljs-keyword">string.notification))
</span>          .setContentText(getString(R.<span class="hljs-keyword">string.ping))
</span>          .setDefaults(Notification.DEFAULT_ALL)
        // 该方法在<span class="hljs-keyword">Android </span><span class="hljs-number">4</span>.<span class="hljs-number">1</span>之前会被忽略
          .setStyle(new NotificationCompat.<span class="hljs-keyword">BigTextStyle()
</span>                .<span class="hljs-keyword">bigText(msg))
</span>        //添加Action <span class="hljs-keyword">Button
</span>        .<span class="hljs-keyword">addAction </span>(R.drawable.ic_stat_dismiss,
                getString(R.<span class="hljs-keyword">string.dismiss), </span>piDismiss)
        .<span class="hljs-keyword">addAction </span>(R.drawable.ic_stat_snooze,
                getString(R.<span class="hljs-keyword">string.snooze), </span>piSnooze)<span class="hljs-comment">;</span></code></pre>
<p>3、其他步骤与普通视图相同</p>
<h4><strong>进度条通知</strong></h4>
<ul>
<li>
<p><strong>明确进度的进度条</strong><br>使用<code>setProgress(max, progress, false)</code>来更新进度。<br>max: 最大进度值<br>progress: 当前进度<br>false: 是否是不明确的进度条</p>
<p>模拟下载过程，示例如下:</p>
<pre class="hljs aspectj"><code class="aspectj"><span class="hljs-keyword">int</span> id = <span class="hljs-number">1</span>;
...
mNotifyManager = (NotificationManager) 
    getSystemService(Context.NOTIFICATION_SERVICE);
mBuilder = <span class="hljs-keyword">new</span> NotificationCompat.Builder(<span class="hljs-keyword">this</span>);
mBuilder.setContentTitle(<span class="hljs-string">"Picture Download"</span>)
    .setContentText(<span class="hljs-string">"Download in progress"</span>)
    .setSmallIcon(R.drawable.ic_notification);

<span class="hljs-comment">// Start a lengthy operation in a background thread</span>
<span class="hljs-keyword">new</span> Thread(
    <span class="hljs-keyword">new</span> Runnable() {
        <span class="hljs-meta">@Override</span>
        <span class="hljs-keyword">public</span> <span class="hljs-function"><span class="hljs-keyword">void</span> <span class="hljs-title">run</span><span class="hljs-params">()</span> </span>{
            <span class="hljs-keyword">int</span> incr;
            <span class="hljs-keyword">for</span> (incr = <span class="hljs-number">0</span>; incr &lt;= <span class="hljs-number">100</span>; incr+=<span class="hljs-number">5</span>) {
                mBuilder.setProgress(<span class="hljs-number">100</span>, incr, <span class="hljs-keyword">false</span>);
                mNotifyManager.notify(id, mBuilder.build());
                <span class="hljs-keyword">try</span> {
                    <span class="hljs-comment">// Sleep for 5 seconds</span>
                    Thread.sleep(<span class="hljs-number">5</span>*<span class="hljs-number">1000</span>);
                } <span class="hljs-keyword">catch</span> (InterruptedException e) {
                    Log.d(TAG, <span class="hljs-string">"sleep failure"</span>);
                }
            }
            mBuilder.setContentText(<span class="hljs-string">"Download complete"</span>)<span class="hljs-comment">//下载完成           </span>
                    .setProgress(<span class="hljs-number">0</span>,<span class="hljs-number">0</span>,<span class="hljs-keyword">false</span>);    <span class="hljs-comment">//移除进度条</span>
            mNotifyManager.notify(id, mBuilder.build());
        }
    }
).start();</code></pre>
<div class="image-package">
<img src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/progress_bar_summary.png" data-original-src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/progress_bar_summary.png" style="cursor: zoom-in;"><br><div class="image-caption"></div>
</div>
<p><br>上图，分别为下载过程中进度条通知 和 下载完成移除进度条后的通知。</p>
</li>
<li>
<p><strong>不确定进度的进度条</strong><br>使用<code>setProgress(0, 0, true)</code>来表示进度不明确的进度条</p>
<p>mBuilder.setProgress(0, 0, true); mNotifyManager.notify(id, mBuilder.build());<br></p>
<div class="image-package">
<img src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/activity_indicator.png" data-original-src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/activity_indicator.png" style="cursor: zoom-in;"><br><div class="image-caption"></div>
</div>
</li>
</ul>
<h4><strong>浮动通知(Heads-up Notifications)</strong></h4>
<p>Android 5.0(API level 21)开始，当屏幕未上锁且亮屏时，通知可以以小窗口形式显示。用户可以在不离开当前应用前提下操作该通知。<br>如图:<br></p><div class="image-package">
<img src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/heads-up.png" data-original-src="https://github.com/ConnorLin/BlogImages/raw/master/2016:04:20/heads-up.png" style="cursor: zoom-in;"><br><div class="image-caption"></div>
</div>
<pre class="hljs haxe"><code class="haxe">NotificationCompat.Builder mNotifyBuilder = 
    <span class="hljs-keyword">new</span> <span class="hljs-type">NotificationCompat</span>.Builder(<span class="hljs-built_in">this</span>)
        .setContentTitle(<span class="hljs-string">"New Message"</span>)
        .setContentText(<span class="hljs-string">"You've received new messages."</span>)
        .setSmallIcon(R.drawable.ic_notify_status)
        .setFullScreenIntent(pendingIntent, <span class="hljs-literal">false</span>);</code></pre>
<p>以下两种情况会显示浮动通知:</p>
<ul>
<li>setFullScreenIntent()，如上述示例。</li>
<li>通知拥有高优先级且使用了铃声和振动</li>
</ul>
<h4><strong>锁屏通知</strong></h4>
<p>Android 5.0(API level 21)开始，通知可以显示在锁屏上。用户可以通过设置选择是否允许敏感的通知内容显示在安全的锁屏上。<br>你的应用可以通过<code>setVisibility()</code>控制通知的显示等级:</p>
<ul>
<li>VISIBILITY_PRIVATE : 显示基本信息，如通知的图标，但隐藏通知的全部内容</li>
<li>VISIBILITY_PUBLIC : 显示通知的全部内容</li>
<li>VISIBILITY_SECRET : 不显示任何内容，包括图标</li>
</ul>
<h4><strong>自定义通知</strong></h4>
<p>Android系统允许使用<a href="http://developer.android.com/reference/android/widget/RemoteViews.html" target="_blank">RemoteViews</a>来自定义通知。<br>自定义普通视图通知高度限制为64dp，大视图通知高度限制为256dp。同时，建议自定义通知尽量简单，以提高兼容性。</p>
<p>自定义通知需要做如下操作:<br>1、创建自定义通知布局<br>2、使用RemoteViews定义通知组件，如图标、文字等<br>3、调用<code>setContent()</code>将RemoteViews对象绑定到NotificationCompat.Builder<br>4、同正常发送通知流程  </p>
<p>注意: 避免为通知设置背景，因为兼容性原因，有些文字可能看不清。  </p>
<h5><strong>定义通知文本样式</strong></h5>
<p>通知的背景颜色在不同的设备和版本中有所不同，Android2.3开始，系统定义了一套标准通知文本样式，建议自定义通知使用标准样式，这样有助于通知文本可见。<br>通知文本样式:</p>
<pre class="hljs stylus"><code class="stylus">Android <span class="hljs-number">5.0</span>之前可用:
android:style/TextAppearance<span class="hljs-selector-class">.StatusBar</span><span class="hljs-selector-class">.EventContent</span><span class="hljs-selector-class">.Title</span>    <span class="hljs-comment">// 通知标题样式  </span>
android:style/TextAppearance<span class="hljs-selector-class">.StatusBar</span><span class="hljs-selector-class">.EventContent</span>             <span class="hljs-comment">// 通知内容样式  </span>

Android <span class="hljs-number">5.0</span>及更高版本:  
android:style/TextAppearance<span class="hljs-selector-class">.Material</span><span class="hljs-selector-class">.Notification</span><span class="hljs-selector-class">.Title</span>         <span class="hljs-comment">// 通知标题样式  </span>
android:style/TextAppearance<span class="hljs-selector-class">.Material</span><span class="hljs-selector-class">.Notification</span>                  <span class="hljs-comment">// 通知内容样式</span></code></pre>
<p>更多通知的标准样式和布局，可参考源码<code>frameworks/base/core/res/res/layout</code>路径下的通知模版如:  </p>
<pre class="hljs crmsh"><code class="crmsh">Android <span class="hljs-number">5.0</span>之前:  
notification_template_base.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_big_base</span>.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_big_picture</span>.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_big_text</span>.<span class="hljs-keyword">xml</span>  

<span class="hljs-title">Android</span> <span class="hljs-number">5.0</span> 及更高版本:  
notification_template_material_base.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_material_big_base</span>.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_material_big_picture</span>.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_part_chronometer</span>.<span class="hljs-keyword">xml</span>  
<span class="hljs-title">notification_template_progressbar</span>.<span class="hljs-keyword">xml</span>  

<span class="hljs-title">等等。</span></code></pre>
<h3><strong>保留Activity返回栈</strong></h3>
<h4><strong>常规Activity</strong></h4>
<p>默认情况下，从通知启动一个Activity，按返回键会回到主屏幕。但某些时候有按返回键仍然留在当前应用的需求，这就要用到<code>TaskStackBuilder</code>了。</p>
<p>1、在manifest中定义Activity的关系</p>
<pre class="hljs xml"><code class="xml">Android 4.0.3 及更早版本
<span class="hljs-tag">&lt;<span class="hljs-name">activity</span>
    <span class="hljs-attr">android:name</span>=<span class="hljs-string">".ResultActivity"</span>&gt;</span>
    <span class="hljs-tag">&lt;<span class="hljs-name">meta-data</span>
        <span class="hljs-attr">android:name</span>=<span class="hljs-string">"android.support.PARENT_ACTIVITY"</span>
        <span class="hljs-attr">android:value</span>=<span class="hljs-string">".MainActivity"</span>/&gt;</span>
<span class="hljs-tag">&lt;/<span class="hljs-name">activity</span>&gt;</span>

Android 4.1 及更高版本
<span class="hljs-tag">&lt;<span class="hljs-name">activity</span>
    <span class="hljs-attr">android:name</span>=<span class="hljs-string">".ResultActivity"</span>
    <span class="hljs-attr">android:parentActivityName</span>=<span class="hljs-string">".MainActivity"</span>&gt;</span>
<span class="hljs-tag">&lt;/<span class="hljs-name">activity</span>&gt;</span></code></pre>
<p>2、创建返回栈PendingIntent</p>
<pre class="hljs pony"><code class="pony"><span class="hljs-type">Intent</span> resultIntent = <span class="hljs-function"><span class="hljs-keyword">new</span> <span class="hljs-title">Intent</span>(this, <span class="hljs-type">ResultActivity</span>.class);
<span class="hljs-title">TaskStackBuilder</span> <span class="hljs-title">stackBuilder</span> = <span class="hljs-title">TaskStackBuilder</span>.<span class="hljs-title">create</span>(this);
<span class="hljs-comment">// 添加返回栈</span>
<span class="hljs-title">stackBuilder</span>.<span class="hljs-title">addParentStack</span>(<span class="hljs-type">ResultActivity</span>.class);
<span class="hljs-comment">// 添加Intent到栈顶</span>
<span class="hljs-title">stackBuilder</span>.<span class="hljs-title">addNextIntent</span>(resultIntent);
<span class="hljs-comment">// 创建包含返回栈的pendingIntent</span>
<span class="hljs-title">PendingIntent</span> <span class="hljs-title">resultPendingIntent</span> =
        <span class="hljs-title">stackBuilder</span>.<span class="hljs-title">getPendingIntent</span>(<span class="hljs-number">0</span>, <span class="hljs-type">PendingIntent</span>.<span class="hljs-type">FLAG_UPDATE_CURRENT</span>);

<span class="hljs-title">NotificationCompat</span>.<span class="hljs-title">Builder</span> <span class="hljs-title">builder</span> = <span class="hljs-title">new</span> <span class="hljs-title">NotificationCompat</span>.<span class="hljs-title">Builder</span>(this);
<span class="hljs-title">builder</span>.<span class="hljs-title">setContentIntent</span>(resultPendingIntent);
<span class="hljs-title">NotificationManager</span> <span class="hljs-title">mNotificationManager</span> =
    (<span class="hljs-type">NotificationManager</span>) <span class="hljs-title">getSystemService</span>(<span class="hljs-type">Context</span>.<span class="hljs-type">NOTIFICATION_SERVICE</span>);
<span class="hljs-title">mNotificationManager</span>.<span class="hljs-title">notify</span>(id, builder.build());</span></code></pre>
<p>上述操作后，从通知启动ResultActivity，按返回键会回到MainActivity，而不是主屏幕。</p>
<h4><strong>特殊Activity</strong></h4>
<p>默认情况下，从通知启动的Activity会在近期任务列表里出现。如果不需要在近期任务里显示，则需要做以下操作:</p>
<p>1、在manifest中定义Activity</p>
<pre class="hljs xml"><code class="xml"><span class="hljs-tag">&lt;<span class="hljs-name">activity</span>
    <span class="hljs-attr">android:name</span>=<span class="hljs-string">".ResultActivity"</span>
    <span class="hljs-attr">android:launchMode</span>=<span class="hljs-string">"singleTask"</span>
    <span class="hljs-attr">android:taskAffinity</span>=<span class="hljs-string">""</span>
    <span class="hljs-attr">android:excludeFromRecents</span>=<span class="hljs-string">"true"</span>&gt;</span>
<span class="hljs-tag">&lt;/<span class="hljs-name">activity</span>&gt;</span></code></pre>
<p>2、构建PendingIntent</p>
<pre class="hljs pony"><code class="pony"><span class="hljs-type">NotificationCompat</span>.<span class="hljs-type">Builder</span> builder = <span class="hljs-function"><span class="hljs-keyword">new</span> <span class="hljs-title">NotificationCompat</span>.<span class="hljs-title">Builder</span>(this);
<span class="hljs-title">Intent</span> <span class="hljs-title">notifyIntent</span> = <span class="hljs-title">new</span> <span class="hljs-title">Intent</span>(this, <span class="hljs-type">ResultActivity</span>.class);

<span class="hljs-comment">// Sets the Activity to start in a new, empty task</span>
<span class="hljs-title">notifyIntent</span>.<span class="hljs-title">setFlags</span>(<span class="hljs-type">Intent</span>.<span class="hljs-type">FLAG_ACTIVITY_NEW_TASK</span> 
        | <span class="hljs-type">Intent</span>.<span class="hljs-type">FLAG_ACTIVITY_CLEAR_TASK</span>);

<span class="hljs-title">PendingIntent</span> <span class="hljs-title">notifyPendingIntent</span> =
        <span class="hljs-title">PendingIntent</span>.<span class="hljs-title">getActivity</span>(this, <span class="hljs-number">0</span>, notifyIntent, 
        <span class="hljs-type">PendingIntent</span>.<span class="hljs-type">FLAG_UPDATE_CURRENT</span>);

<span class="hljs-title">builder</span>.<span class="hljs-title">setContentIntent</span>(notifyPendingIntent);
<span class="hljs-title">NotificationManager</span> <span class="hljs-title">mNotificationManager</span> =
    (<span class="hljs-type">NotificationManager</span>) <span class="hljs-title">getSystemService</span>(<span class="hljs-type">Context</span>.<span class="hljs-type">NOTIFICATION_SERVICE</span>);
<span class="hljs-title">mNotificationManager</span>.<span class="hljs-title">notify</span>(id, builder.build());</span></code></pre>
<p>上述操作后，从通知启动ResultActivity，此Activity不会出现在近期任务列表中。</p>
<h3><strong>通知常见属性和常量</strong></h3>
<h4><strong>通知的提醒方式</strong></h4>
<p><strong>1、声音提醒</strong></p>
<ul>
<li>默认声音<br>notification.defaults |= Notification.DEFAULT_SOUND;</li>
<li>自定义声音<br>notification.sound = Uri.parse("file:///sdcard0/notification.ogg");</li>
</ul>
<p><strong>2、震动提醒</strong></p>
<ul>
<li>默认振动<br>notification.defaults |= Notification.DEFAULT_VIBRATE;</li>
<li>自定义振动<br>long[] vibrate = {100, 200, 300, 400}; //震动效果<br>// 表示在100、200、300、400这些时间点交替启动和关闭震动 notification.vibrate = vibrate;</li>
</ul>
<p><strong>3、闪烁提醒</strong></p>
<ul>
<li>默认闪烁<br>notification.defaults |= Notification.DEFAULT_LIGHTS;</li>
<li>自定义闪烁<br>notification.ledARGB = 0xff00ff00; // LED灯的颜色，绿灯<br>notification.ledOnMS = 300; // LED灯显示的毫秒数，300毫秒<br>notification.ledOffMS = 1000; // LED灯关闭的毫秒数，1000毫秒<br>notification.flags |= Notification.FLAG_SHOW_LIGHTS; // 必须加上这个标志</li>
</ul>
<h4><strong>常见的Flags</strong></h4>
<ul>
<li>FLAG_AUTO_CANCEL<br>当通知被用户点击之后会自动被清除(cancel)</li>
<li>FLAG_INSISTENT<br>在用户响应之前会一直重复提醒音</li>
<li>FLAG_ONGOING_EVENT<br>表示正在运行的事件</li>
<li>FLAG_NO_CLEAR<br>通知栏点击“清除”按钮时，该通知将不会被清除</li>
<li>FLAG_FOREGROUND_SERVICE<br>表示当前服务是前台服务<br>更多Notification属性详见<a href="http://developer.android.com/reference/android/app/Notification.html" target="_blank">Notification</a>。</li>
</ul>
<p>That's all！ 更多通知知识点等待你来发掘，欢迎补充!  </p>
<p><strong>参考资料</strong><br><a href="http://developer.android.com/guide/topics/ui/notifiers/notifications.html" target="_blank">Notifications</a></p>

        </div>
        <!--  -->

        <div class="show-foot">
          <a class="notebook" href="/nb/3947614">
            <i class="iconfont ic-search-notebook"></i> <span>Android</span>
</a>          <div class="copyright" data-toggle="tooltip" data-html="true" data-original-title="转载请联系作者获得授权，并标注“简书作者”。">
            © 著作权归作者所有
          </div>
          <div class="modal-wrap" data-report-note="">
            <a id="report-modal">举报文章</a>
          </div>
        </div>
    </div>

# The SRE Spam Filter
## Adding to the Spam Filter
Adding to the Spam Filter is extremely easy. Go to the ```FilterHandler#init()```function<sup>1</sup> and write (in a new line):
```
add(true/false (whether or not it is a concatenation filter), config.yourVarName, needsDungeons (optional),
new String[]{"each", "necessary", "string for cancellation"} (optional));
```
### Adding a Config Entry
Please see the [Config](https://github.com/theCudster/SkyblockReinvented/docs/CONFIG.md) documentation for more info on this. 
Make sure your variable name is descriptive, especially if you plan on uploading to GitHub!
### The Filter.java File
<details><summary>Variable Descriptions</summary>
<li><code>private String[] messageArr</code>: list of Strings that should be canceled.</li>
<li><code>private boolean shouldCancel</code>: whether to cancel the message. Usually a <code>config.varName</code></li>
<li><code>concatCheck</code>: list of Strings that need to be in a message for it to be canceled. optional</li>
<li><code>needsDungeons</code>: whether the user needs to be in dungeons for the message to be canceled</li>
</details>
<details><summary>Function Descriptions</summary>
<li><code>public boolean shouldCancel(ClientChatReceivedEvent event)</code>
Checks whether to cancel a chat message given a <code>ClientChatReceivedEvent</code>. (Using all other methods)
</li>
<li><code>public boolean checkConcat(ClientChatReceivedEvent event)</code>
Checks whether the event should be canceled based on <code>concatCheck</code> Strings.</li>
<li><code>public boolean check(ClientChatReceivedEvent event)</code> Checks whether the event should be canceled based on <code>messageArr</code>.</li>
<li><code>public boolean checkDungeons()</code> Returns whether an event should be canceled (regarding dungeons). Helper method.</li>
<li><code>public boolean getShouldCancel()</code>Getter method for <code>shouldCancel</code>.</li>
</details>

### The FilterConcat.java File
<details><summary>Variable Descriptions</summary>
<li><code>private String[] concatCheck</code>: list of Strings that need to be in a message for it to be canceled.</li>
<li><code>private boolean shouldCancel</code>: whether to cancel the message. Usually a <code>config.varName</code></li>
<li><code>private boolean needsDungeons</code>: whether the user needs to be in dungeons for the message to be canceled</li>
</details>
<details><summary>Function Descriptions</summary>
<li><code>public boolean shouldCancel(ClientChatReceivedEvent event)</code>
Checks whether to cancel a chat message given a <code>ClientChatReceivedEvent</code>. (Using all other methods)
</li>
<li><code>public boolean checkConcat(ClientChatReceivedEvent event)</code>
Checks whether the event should be canceled based on <code>concatCheck</code> Strings.</li>
<li><code>public boolean checkDungeons()</code> Returns whether an event should be canceled (regarding dungeons). Helper method.</li>
<li><code>public boolean getShouldCancel()</code>Getter method for <code>shouldCancel</code>.</li>
</details>

### Adding an Array to ArrStorage.java
The ArrStorage.java file is a pretty self-explanatory file; it is storage of arrays. If your spam filter has a lot of messages
(>5 is a good rule of thumb), it is advisable to use an array storage. Creating a new array storage is relatively simple; 
simply go to the end of the 
## Upload to GitHub
If you're just doing this for personal use, then you don't have to upload it to GitHub if you don't want to.
However, if you want this to be a feature in SRE, it probably can be!  
See the [GitHub Startup](https://github.com/theCudster/SkyblockReinvented/docs/GITHUB.md) documentation for more info on uploading.
<details><summary><sup>1</sup>: What does this do?</summary>
This is simply a way to easily add Filter.java / FilterConcat.java instances to the <code>filters</code> ArrayList. It has two options (overloaded methods):
<li><code>add(boolean concat, String[] messages, boolean shouldCancel, String[] concatCheck)</code></li>
<li><code>add(String[] messages, boolean shouldCancel, boolean dungeons, String[] concatCheck)</code></li>
The parameter descriptions are as follows:
<li><code>String[] messages</code>: the array of messages to block</li>
<li><code>boolean shouldCancel</code>: whether to cancel the message. In the format of <code>config.yourVarName</code></li>
<li><code>dungeons</code>: whether the user needs to be in dungeons for it to be canceled. The equivalent of saying <code>if (Utils.inDungeons)</code></li>
<li><code>concatCheck</code>: a list of Strings that need to be in a message for it to be canceled</li>
</details>
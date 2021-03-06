angular.module('history').service('chromeAPI', function ($q) {

    var saveData = function(key, value) {
       // Get a value saved in a form.
       //var theValue = textarea.value;
       // Check that there's some code there.
       if (!value) {
         alert('Error: No value specified');
         return;
       }
       var data = {};
       data[key] = value;
       // Save it using the Chrome extension storage API.
       chrome.storage.sync.set(data, function() {
         // Notify that we saved.
         //alert(key + ' saved into chrome storage!');
       });
    };

    var _this = this;
    this.data = [];
    this.authToken = "";
    this.profile = "";

    /**
    * checks whether user is already authenticated or not
    */
    this.isLoggedIn = function(success, failure) {
    	chrome.storage.sync.get("auth_token", function(keys) {
            //alert(keys["auth_token"]);
    		//console.log("feteched auth_token: " + JSON.stringify(keys));
    		if (keys["auth_token"] == undefined) failure("undefined");
    		else success(keys["auth_token"]);
    	});
    };

    /**
    * performs login action for the user
    */
    this.doLogin = function(success, failure) {
    	chrome.identity.getAuthToken({ 'interactive' : true }, function (token) {
		      if (chrome.runtime.lastError) {
		          alert(chrome.runtime.lastError.message);
		          failure(chrome.runtime.lastError.message);
		      }
		      else {
					var x = new XMLHttpRequest();
					x.open('GET', 'https://www.googleapis.com/oauth2/v2/userinfo?alt=json&access_token=' + token);
					x.onload = function() {
						//alert("auth_token: " + x.response);
                        console.log(token);
						saveData("auth_token", token);
                        saveData("profile", x.response);
						this.authToken = token;
                        this.profile = x.response;
						console.log(this.authToken);
					};
					x.send();
					success(token);
		      }
		  });
    };

    /**
    * fetches the current url of the browser
    */
    this.getCurrentURL = function(success, failure) {
        /*chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab)  {
            console.log(changeInfo);
            if (changeInfo.status == "complete") {
                success(info);
            }
        });
        chrome.tabs.query({'active': true, 'lastFocusedWindow': true}, function (tabs) {
            success(tabs[0].url);
        });*/
        chrome.tabs.query({'active': true, 'windowId': chrome.windows.WINDOW_ID_CURRENT},
           function(tabs) {
              success(tabs[0].url);
           }
        );
    };

    /**
    * updates the new url in the browser
    */
    this.getUpdatedURL = function(success, failure) {
        /*chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
            if(changeInfo.status == "complete" && ("url" in changeInfo)) {
                success(changeInfo.url);
            }
        });*/
    }
    /**
    * fetches the profile if of the user
    */
    this.getProfileId = function(success, failure) {
        chrome.identity.getAuthToken({ 'interactive' : true }, function (token) {
              if (chrome.runtime.lastError) {
                  alert(chrome.runtime.lastError.message);
                  failure(chrome.runtime.lastError.message);
              }
              else {
                    var x = new XMLHttpRequest();
                    x.open('GET', 'https://www.googleapis.com/oauth2/v2/userinfo?alt=json&access_token=' + token);
                    x.onload = function() {
                        //alert("auth_token: " + x.response);
                        //console.log(token);
                        saveData("auth_token", token);
                        saveData("profile", x.response);
                        this.authToken = token;
                        this.profile = x.response;
                        console.log(this.authToken);
                        console.log(this.profile);
                        var profile = JSON.parse(this.profile);
                        success(profile.id);
                    };
                    x.send();
              }
          });
    };
});
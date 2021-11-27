/**
 * Login management
 */

(function() { // avoid variables ending up in the global scope

  document.getElementById("loginbutton").addEventListener('click', (e) => {
    var form = e.target.closest("form");
    if (form.checkValidity()) {
      makeCall("POST", 'CheckLogin', e.target.closest("form"),
        function(req) {
          if (req.readyState == XMLHttpRequest.DONE) {
            var message = JSON.parse(req.responseText);
            switch (req.status) {
              case 200:
            	sessionStorage.setItem('username', message.name+" "+message.surname);
            	if(message.role === "student"){
            		window.location.href = "HomeS.html";
            	} else {
            		window.location.href = "HomeT.html";
            	}
                break;
              case 400: // bad request
                document.getElementById("errormessage").textContent = message;
                break;
              case 401: // unauthorized
                  document.getElementById("errormessage").textContent = message;
                  break;
              case 500: // server error
            	document.getElementById("errormessage").textContent = message;
                break;
            }
          }
        }
      );
    } else {
    	 form.reportValidity();
    }
  });

})();
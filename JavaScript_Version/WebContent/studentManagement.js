	(function() { 
		
	  var courses, appelli, esito
	    pageOrchestrator = new PageOrchestrator(); 

	  window.addEventListener("load", () => {
	    if (sessionStorage.getItem("username") == null) {
	      window.location.href = "index.html";
	    } else {
	      pageOrchestrator.start(); 
	      pageOrchestrator.refresh();
	    } 
	  }, false);



	  function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function() {
	      messagecontainer.textContent = this.username;
	    }
	  }

	  function Courses(_alert, _listcontainer, _listcontainerbody) {
	    this.alert = _alert;
	    this.listcontainer = _listcontainer;
	    this.listcontainerbody = _listcontainerbody;

	    this.reset = function() {
	      this.listcontainer.style.visibility = "hidden";
	    }

	    this.show = function() {
	      var self = this;
	      makeCall("GET", "GetCourses", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var coursesToShow = JSON.parse(req.responseText);
	              if (coursesToShow.length == 0) {
	                self.alert.textContent = "Non ci corsi a te associati.";
	                return;
	              }
	              self.update(coursesToShow);
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          } 
	        }
	      );
	    };


	    this.update = function(arrayCourses) {
	      var row, destcell, linkcell, anchor;
	      this.listcontainerbody.innerHTML = ""; 
	      var self = this;
	      arrayCourses.forEach(function(course) { 
	        row = document.createElement("tr");
	        destcell = document.createElement("td");
	        destcell.textContent = course.name;
	        row.appendChild(destcell);
	        linkcell = document.createElement("td");
	        anchor = document.createElement("a");
	        linkcell.appendChild(anchor);
	        linkText = document.createTextNode("Vedi appelli");
	        anchor.appendChild(linkText);
	        anchor.setAttribute('courseid', course.id);
	        anchor.addEventListener("click", (e) => {
	          pageOrchestrator.refresh();
	          appelli.show(e.target.getAttribute("courseid"));
	        }, false);
	        anchor.href = "#";
	        row.appendChild(linkcell);
	        self.listcontainerbody.appendChild(row);
	      });
			this.listcontainer.style.visibility = "visible";
	    }

	  }
	  
	  function Appelli(_alert, _listcontainer, _listcontainerbody) {
	    this.alert = _alert;
	    this.listcontainer = _listcontainer;
	    this.listcontainerbody = _listcontainerbody;

	    this.reset = function() {
	      this.listcontainer.style.visibility = "hidden";
	    }

	    this.show = function(courseid) {
	      var self = this;
	      makeCall("GET", "GetAppelli?courseid="+courseid, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var appelliToShow = JSON.parse(req.responseText);
	              if (appelliToShow.length == 0) {
	                self.alert.textContent = "Non ci sono appelli per questo corso a cui sei iscritto!";
	                return;
	              }
	              self.update(appelliToShow);
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          } 
	        }
	      );
	    };


	    this.update = function(arrayAppelli) {
	      var row, linkcell, anchor;
	      this.listcontainerbody.innerHTML = ""; 
	      var self = this;
	      arrayAppelli.forEach(function(appello) {
	        row = document.createElement("tr");
	        linkcell = document.createElement("td");
	        anchor = document.createElement("a");
	        linkcell.appendChild(anchor);
	        linkText = document.createTextNode(appello.date);
	        anchor.appendChild(linkText);
	        anchor.setAttribute('appelloid', appello.id);
	        anchor.addEventListener("click", (e) => {
	          esito.show(e.target.getAttribute("appelloid"));
	        }, false);
	        anchor.href = "#";
	        row.appendChild(linkcell);
	        self.listcontainerbody.appendChild(row);
	      });
			this.listcontainer.style.visibility = "visible";
	    }

	  }
	  
	   function Esito(_alert, _container, _form) {
	    this.alert = _alert;
	    this.container = _container;
	    this.form = _form;
	    
	    this.registerEvents = function() {
	      this.form.querySelector("input[type='button']").addEventListener('click', (e) => {
			  var self = this, formToSend = e.target.closest("form");
			  makeCall("POST", 'Rifiuta', formToSend,
	            function(req) {
	              if (req.readyState == XMLHttpRequest.DONE) {
	                if (req.status == 200) {
	                  self.reset();
	                } else {
	                  pageOrchestrator.refresh();
	                }
	              }
	            }
	          );
	      });
	    };

	    this.reset = function() {
	      this.container.style.visibility = "hidden";
	      this.form.style.visibility= "hidden";
	    }

	    this.show = function(appelloid) {
	      var self = this;
	      makeCall("GET", "GetInfo?appelloid="+appelloid, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var esitoToShow = JSON.parse(message);
	              if (esitoToShow.state === 'EMPTY' || esitoToShow.state === 'NOTPUBLISHED') {
					self.reset();
	                self.alert.textContent = "Voto non ancora definito!";
	                return;
	              } else if (esitoToShow.state === 'REFUSED'){
		                self.reset();
		              	self.alert.textContent = "Hai rifiutato il voto!";
		          
	              }
	              self.update(esitoToShow); 
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          } 
	        }
	      );
	    };


	    this.update = function(newEsito) {
	      var destcell;
	      this.container.innerHTML = ""; 
	      var self = this;
	        destcell = document.createElement("p");
	        destcell.textContent = "Matricola: "+ newEsito.matricola;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Nome: "+ newEsito.name;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Cognome: "+ newEsito.surname;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Email: "+ newEsito.email;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Corso di Laurea: "+ newEsito.cdl;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Nome del corso: "+ newEsito.courseName;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Docente del corso: "+ newEsito.teacherName + " " + newEsito.teacherSurname;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Data appello: "+ newEsito.date;
	        self.container.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Voto: "+ newEsito.grade;
	        self.container.appendChild(destcell);
	        
	        if(newEsito.rifiutabile == true){
	       		self.form.appelloid.value = newEsito.appelloid; 
		        self.form.style.visibility = "visible";
	            
	        } else {
	        	self.form.style.visibility = "hidden";
	        }
	      
			this.container.style.visibility = "visible";
	    }

	  }
	  

	  function PageOrchestrator() {
	    var alertContainer = document.getElementById("id_alert");
	    this.start = function() {
	      personalMessage = new PersonalMessage(sessionStorage.getItem('username'),
	        document.getElementById("id_username"));
	      personalMessage.show();

	      courses = new Courses(
	        alertContainer,
	        document.getElementById("id_listcontainer"),
	        document.getElementById("id_listcontainerbody"));
	        
	      appelli= new Appelli(
	        alertContainer,
	        document.getElementById("id_appellicontainer"),
	        document.getElementById("id_appellicontainerbody"));

	      esito = new Esito(
	      	alertContainer,
	      	document.getElementById("id_esitocontainer"),
	      	document.getElementById("id_refuseform"));
	      esito.registerEvents();
	      
	      document.querySelector("a[href='Logout']").addEventListener('click', () => {
	        window.sessionStorage.removeItem('username');
	      })
	    };


	    this.refresh = function() {
	      alertContainer.textContent = "";
	      courses.reset();
	      appelli.reset();
	      esito.reset();
	      courses.show(); 
	    };
	  }
	})();
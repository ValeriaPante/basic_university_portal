	(function() {
		
	  var courses, appelli, iscritti, verbale
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
	              var coursesToShow = JSON.parse(message);
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
	              var appelliToShow = JSON.parse(message);
	              if (appelliToShow.length == 0) {
	                self.alert.textContent = "Non ci sono appelli per questo corso!";
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
	          verbale.reset();
	          iscritti.reset();
	          iscritti.show(e.target.getAttribute("appelloid"));
	        }, false);
	        anchor.href = "#";
	        row.appendChild(linkcell);
	        self.listcontainerbody.appendChild(row);
	      });
			this.listcontainer.style.visibility = "visible";
	    }

	  }
	  
	  function Iscritti(_alert, _listcontainer, _listcontainerbody, _studdata, _formgrade, _pubblicabutton, _verbalizzabutton) {
	    this.alert = _alert;
	    this.listcontainer = _listcontainer;
	    this.listcontainerbody = _listcontainerbody;
	    this.studdata = _studdata;
	    this.formgrade = _formgrade;
	    this.pubblicabutton = _pubblicabutton;
	    this.verbalizzabutton = _verbalizzabutton;
	    
	    this.registerEvents = function() {
	      this.formgrade.querySelector("input[type='button']").addEventListener('click', (e) => {
			  var self = this, formToSend = e.target.closest("form");
			  makeCall("POST", 'Modifica', formToSend,
	            function(req) {
	              if (req.readyState == XMLHttpRequest.DONE) {
					var message = req.responseText;
	                if (req.status == 200) {
	                  self.reset();
	                  self.show(formToSend.appelloid.value);
	                } else {
	                  self.alert.textContent = message;
	                }
	              }
	            }
	          );
	      });
	      
	      this.pubblicabutton.querySelector("input[type='button']").addEventListener('click', (e) => {
			  var self = this, formToSend = e.target.closest("form");
			  makeCall("POST", 'Pubblica', formToSend,
	            function(req) {
	              if (req.readyState == XMLHttpRequest.DONE) {
	                var message = req.responseText;
	                if (req.status == 200) {
	                  var iscrittiToShow = JSON.parse(message);
						self.update(iscrittiToShow);
					
	                  
	                } else {
	                  pageOrchestrator.refresh();
	                }
	              }
	            }
	          );
	      });
	      
	      this.verbalizzabutton.addEventListener('click', (e) => {
			  verbale.show(e.target.getAttribute("appelloid"));
	      });
	    };

	    this.reset = function() {
		  this.alert.textContent = "";
	      this.listcontainerbody.innerHTML = "";
	      this.listcontainer.style.visibility = "hidden";
	      this.studdata.innerHTML = "";
	      this.studdata.style.visibility = "hidden";
	      this.pubblicabutton.style.visibility = "hidden";
	      this.verbalizzabutton.style.visibility = "hidden";
	      this.formgrade.style.visibility = "hidden";
	    }

	    this.show = function(appelloid) {
	      var self = this;
	      makeCall("GET", "GetIscritti?appelloid="+appelloid, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var iscrittiToShow = JSON.parse(message);
	              if (iscrittiToShow.length == 0) {
	                self.alert.textContent = "Non ci sono iscritti!";
	                return;
	              }
	              self.update(iscrittiToShow); 
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          } 
	        }
	      );
	    };


	    this.update = function(arrayIscritti) {
	      var row, destcell, linkcell, anchor;
	      this.listcontainerbody.innerHTML = ""; 
	      var self = this;
	      arrayIscritti.forEach(function(iscritto) {
	        row = document.createElement("tr");
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.matricola;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.name;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.surname;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.email;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.cdl;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.grade;
	        row.appendChild(destcell);
	        destcell = document.createElement("td");
	        destcell.textContent = iscritto.state;
	        row.appendChild(destcell);
	        
	        if(iscritto.state === 'NOTPUBLISHED' || iscritto.state === 'EMPTY'){
		        linkcell = document.createElement("td");
		        anchor = document.createElement("a");
		        linkcell.appendChild(anchor);
		        linkText = document.createTextNode("Modifica");
		        anchor.appendChild(linkText);
		        anchor.setAttribute('appelloid', iscritto.appelloid); 
		        anchor.setAttribute('studid', iscritto.id);
		        anchor.addEventListener("click", (e) => {
		          self.modifica(e.target.getAttribute("appelloid"), e.target.getAttribute("studid"));
		        }, false);
		        anchor.href = "#";
		        row.appendChild(linkcell);
	        }
	        
	        self.listcontainerbody.appendChild(row);
	        self.pubblicabutton.appelloid.value = iscritto.appelloid;
	        self.verbalizzabutton.setAttribute('appelloid', iscritto.appelloid);
	      });
			this.listcontainer.style.visibility = "visible";
			this.listcontainerbody.style.visibility = "visible";
			this.pubblicabutton.style.visibility = "visible";
	      	this.verbalizzabutton.style.visibility = "visible";
	    }
	    
	    this.modifica = function(appelloid, studid){
	    	 var self = this, elem;
	      makeCall("GET", "Modifica?appelloid="+appelloid+"&studId="+studid, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var data = JSON.parse(message);
	              
				  self.alert.textContent = "";
	              self.studdata.innerHTML = "";
	              
	              elem = document.createElement("p");
			      elem.textContent = "Matricola: "+data.matricola;
			      self.studdata.appendChild(elem);
			      elem = document.createElement("p");
			      elem.textContent = "Nome: "+data.name;
			      self.studdata.appendChild(elem);
			      elem = document.createElement("p");
			      elem.textContent = "Cognome: "+data.surname;
			      self.studdata.appendChild(elem);
			      elem = document.createElement("p");
			      elem.textContent = "Email: "+data.email;
			      self.studdata.appendChild(elem);
			      elem = document.createElement("p");
			      elem.textContent = "Corso di Laurea: "+data.cdl;
			      self.studdata.appendChild(elem); 
			      self.studdata.style.visibility = "visible";
			      self.formgrade.appelloid.value = data.appelloid;
			      self.formgrade.studid.value = data.id;
			      self.formgrade.grade.value = data.grade;
			      self.formgrade.style.visibility = "visible";
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          } 
	        }
	      );
	    }

	  }
	  
	  function Verbale(_alert, _verbalecontainer, _iscrittiverbale, _iscrittiverbalebody) {
	    this.alert = _alert;
	    this.verbalecontainer = _verbalecontainer;
	    this.iscrittiverbale = _iscrittiverbale;
	    this.iscrittiverbalebody = _iscrittiverbalebody;

	    this.reset = function() {
	      this.verbalecontainer.style.visibility = "hidden";
	      this.verbalecontainer.innerHTML = "";
	      this.iscrittiverbale.style.visibility = "hidden";
	      this.iscrittiverbalebody.innerHTML = "";
	    }

	    this.show = function(appelloid) {
	      var self = this;
	      makeCall("GET", "GetVerbale?appelloid="+appelloid, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var verbaleToShow = JSON.parse(message);
	              
	              self.update(verbaleToShow);
	            } else {
	            	pageOrchestrator.refresh();
	          	}
	          }
	        }
	      );
	    };


	    this.update = function(newVerbale) {
	       var row, destcell;
	      this.verbalecontainer.innerHTML = ""; 
	      var self = this;
	        destcell = document.createElement("h2");
	        destcell.textContent = "Verbale";
	        self.verbalecontainer.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Data creazione: "+ newVerbale.date;
	        self.verbalecontainer.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Ora creazione: "+ newVerbale.time;
	        self.verbalecontainer.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Nome del corso: "+ newVerbale.courseName;
	        self.verbalecontainer.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Docente del corso: "+ newVerbale.teacherName + " "+ newVerbale.teacherSurname;
	        self.verbalecontainer.appendChild(destcell);
	        
	        destcell = document.createElement("p");
	        destcell.textContent = "Data appello: "+ newVerbale.appelloDate;
	        self.verbalecontainer.appendChild(destcell);
	        
	        self.iscrittiverbalebody.innerHTML ="";
	        
	        newVerbale.students.forEach(function(iscritto) {
	        	row = document.createElement("tr");
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.matricola;
		        row.appendChild(destcell);
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.name;
		        row.appendChild(destcell);
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.surname;
		        row.appendChild(destcell);
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.email;
		        row.appendChild(destcell);
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.cdl;
		        row.appendChild(destcell);
		        destcell = document.createElement("td");
		        destcell.textContent = iscritto.grade;
		        row.appendChild(destcell);
		        
		        self.iscrittiverbalebody.appendChild(row);
	        })
	      
			self.verbalecontainer.style.visibility = "visible";
			self.iscrittiverbale.style.visibility = "visible";
			self.iscrittiverbalebody.style.visibility = "visible";
			iscritti.reset();
			
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

	      iscritti= new Iscritti(
	        alertContainer,
	        document.getElementById("id_iscritticontainer"),
	        document.getElementById("id_iscritticontainerbody"),
	        document.getElementById("id_studdata"),
	        document.getElementById("id_formgrade"),
	        document.getElementById("id_pubblicaform"),
	        document.getElementById("verbalizza"));
	      iscritti.registerEvents();
	      
	      verbale = new Verbale(
	      	alertContainer,
	      	document.getElementById("id_verbalecontainer"),
	      	document.getElementById("id_iscrittiverbale"),
	      	document.getElementById("id_iscrittiverbalebody"));
	      
	      document.querySelector("a[href='Logout']").addEventListener('click', () => {
	        window.sessionStorage.removeItem('username');
	      })
	    };


	    this.refresh = function() {
	      alertContainer.textContent = "";
	      courses.reset();
	      appelli.reset();
	      iscritti.reset();
	      verbale.reset();
	      courses.show(); 
	    };
	  }
	})();
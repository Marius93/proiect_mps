var app = angular.module("app",[]);

app
	.controller("caruselCtrl",["$scope","$http","$interval",
	function($scope, $http, $interval){
		if (!$scope.users) {
			$scope.users = [];
		}
		$scope.showGreeting = function() {
			if ($scope.verify()) {
				$scope.users.push({username:$scope.username,
									password:$scope.password});
			}
			// TODO aici tre sa trimitem la server numele de utilizator (users)
			
			$http.post('localhost:8080/login', users)
				success(function(data, status, headers, config) {
				// this callback will be called asynchronously
				// when the response is available
				}).
				error(function(data, status, headers, config) {
					//return status;
					console.log(status);
				// called asynchronously if an error occurs
				// or server returns response with an error status.
				});

		}
		$scope.verify = function() {
			var a = $scope.username;
			var b = $scope.password;
			return (a && b);
		}
		$scope.$watch("users", function(newVal, oldVal ){
			console.log($scope.users);
			console.log("The value of 'users' has changed");
		},true);

		//metoda login user 1 - autentificare ok; 0 - autentificar nu e ok
		$scope.loginUserFunction = function(var username,var password) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
   				 if (xhttp.readyState == 4 && xhttp.status == 200) {
      					console.log(xhttp.responseText);
      					if(xhttp.responseText.localeCompare('OK') == 0)
      						return 1;
      					else 
      						return 0;
   				 }
 			 };
			xhttp.open("POST", "http://localhost:8080/login", true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send("username=" + username + "&password=" + password);
		}

		//metoda creare user la fel ca cea de sus
		$scope.createUserFunction = function(var username,var password){
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
   				 if (xhttp.readyState == 4 && xhttp.status == 200) {
      					console.log(xhttp.responseText);
      					if(xhttp.responseText.localeCompare('OK') == 0)
      						return 1;
      					else 
      						return 0;
   				 }
 			 };
			xhttp.open("POST", "http://localhost:8080/createUser", true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send("username=" + username + "&password=" + password);
		}

		$scope.singlePlayer = function() {
			//daca se alege singlePlayer trimit la server asta
		}

		$scope.multiPlayer = function() {
			//daca se alege multiPlayer trimit la server asta
		}
		$scope.ok = 0;
		$scope.litere = ['','','','','','','','','']; //primesc de la server
		$scope.generate = function() {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
   				 if (xhttp.readyState == 4 && xhttp.status == 200) {
      					console.log(xhttp.responseText); 
      					var arr = xhttp.responseText.split();
      					for(var i=0;i<9;i++)
      						$scope.litere[i] = arr[i];
   				 }
 			 };
			xhttp.open("GET", "http://localhost:8080/startGame", true);
			xhttp.send();
			
			//pornesc timerul
			$scope.fight();
		}

		$scope.format = 'M/d/yy h:mm:ss a';
        $scope.blood_2 = 0;
        $scope.min = 2;
        $scope.terminat = 0;

        //pentru numele jucatorului trimite prin  url numele sau... ceva genul pagin_web_urmatoare?username=Ciprian

        var stop;
        $scope.fight = function() {
			// Don't start a new fight if we are already fighting
			if ( angular.isDefined(stop) ) return;

			stop = $interval(function() {
				if ($scope.blood_2 > 0) {
				  	$scope.blood_2 = $scope.blood_2 - 1;
				} else if($scope.min > 0){
				  	$scope.min = $scope.min - 1;
				  	$scope.blood_2 = 59;
				}
				if($scope.blood_2 == 0 && $scope.min == 0 && $scope.terminat== 0) {
				  	alert("Timpul a expirat! \n\nScorul tau este: " + $scope.score);
				  	$scope.terminat = 1;
				}
			}, 1000);
        };

		for(var i = 0; i < $scope.litere.length; i++)
			$scope.litere[i] = $scope.litere[i].toUpperCase();


		$scope.score = 0;
		$scope.nameGet = "";

		

		$scope.verificaCuvant = function() {
			//inainte de toate astea sa se verifice daca cuvantul contine doar literele din joc
			//daca da se executa codul asta de jos 
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
   				 if (xhttp.readyState == 4 && xhttp.status == 200) {
      					console.log(xhttp.responseText);
      					$scope.score += parseInt(xhttp.responseText);
   				 }
 			 };
			xhttp.open("POST", "http://localhost:8080/validateWords", true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send("words=" + $scope.word);

			//linia de sub se executa indiferent de validare, ok ?
			$scope.word= "";
		}
	}])
	.directive("myCurrentTime", ["$interval", "dateFilter",
      function($interval, dateFilter) {
        // return the directive link function. (compile function not needed)
        return function(scope, element, attrs) {
          var format,  // date format
              stopTime; // so that we can cancel the time updates

          // used to update the UI
          function updateTime() {
            element.text(dateFilter(new Date(), format));
          }

          // watch the expression, and update the UI on change.
          scope.$watch(attrs.myCurrentTime, function(value) {
            format = value;
            updateTime();
          });

          stopTime = $interval(updateTime, 1000);

          // listen on DOM destroy (removal) event, and cancel the next UI update
          // to prevent updating time after the DOM element was removed.
          element.on('$destroy', function() {
            $interval.cancel(stopTime);
          });
        }
      }]);
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<style>
   
    h1{
        text-align: center;
        font-style: initial;
        color: rgb(0, 119, 255);
        
    }
    h2{
        text-align: center;
        font-style: initial;
        color: rgb(180, 6, 35);
        
    }
    
    form{
        display: flex; 
        flex-direction: column;
        width: 30%;
        gap: 5px;
        box-shadow: rgba(0, 0, 0, 0.05) 0px 6px 24px 0px, rgba(0, 0, 0, 0.08) 0px 0px 0px 1px; 
        padding: 30px ;
        margin: auto;
        border-radius: 10px;
    }
    input{
        padding: 15px;
        border-radius: 5px;
        font-size: 14px;
    }
    button{
        width: 30%;
        padding: 20px;
        margin-left: 33%;
        border-radius: 5px;
        border: 0px;
        text-align: center;
        justify-content: center;
        /* color: red;         */
        box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;
        
    }
    button:hover{
        background-color: rgb(0, 255, 128);
        color: rgb(12, 5, 5);
        box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    }
</style>
<body>

<h1>  Login Page </h1>
<h2>${errorMsg}</h2>

<form method="post">
    <input type="text" name="username" placeholder="Email or Username">
    <br>
    <input type="password" name="password" placeholder="password">
    <br>
   
    <textarea name="osName" id="os"  hidden></textarea>
    <textarea name="lattitude" id="latt"  hidden></textarea>
    <textarea name="Longitude" id="long"  hidden></textarea>
    <textarea name="browserName" id="bn"  hidden></textarea>
    

    <br>

    
    <button> Login </button>
</form>
<script>

    //operating system fetcher

        var oss = document.getElementById("os"); 
		// HTMLDocument.prototype.e = document.getElementById;	
		
		
		var Name = "Not known";
		if (navigator.appVersion.indexOf("Win") != -1)
         Name =	"Windows OS";
		if (navigator.appVersion.indexOf("Mac") != -1)
         Name =	"MacOS";
		if (navigator.appVersion.indexOf("X11") != -1) 
         Name =	"UNIX OS";
		if (navigator.appVersion.indexOf("Linux") != -1)
         Name = "Linux OS";		
		oss.innerHTML = Name;
                
                    // location fetcher
                    var latt1 = document.getElementById("latt");
                    var long1 = document.getElementById("long");
                
                   
                        if (navigator.geolocation) {
                            navigator.geolocation.getCurrentPosition(showPosition);
                        } else { 
                            latt1.innerHTML = "Geolocation is not supported by this browser.";
                        }                   

                                function showPosition(position) {
                                let num1 =  position.coords.latitude;
                                let num2 =  position.coords.longitude;
                                latt1.innerText = num1.toString();
                                long1.innerText = num2.toString();
                                }

      // browser fetcher
                 
                    let userAgent = navigator.userAgent;
                    let browserName;
                 
                    if(userAgent.match(/chrome|chromium|crios/i)){
                     browserName = "chrome";
                   }else if(userAgent.match(/firefox|fxios/i)){
                     browserName = "firefox";
                   }  else if(userAgent.match(/safari/i)){
                     browserName = "safari";
                   }else if(userAgent.match(/opr\//i)){
                     browserName = "opera";
                   } else if(userAgent.match(/edg/i)){
                     browserName = "edge";
                   }else{
                     browserName="No browser detection";
                   }
                 
                   document.getElementById("bn").innerText= browserName ;                           
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data Siplay page</title>
<style>

    body{
        background-image: linear-gradient( 95.2deg, rgba(173,252,234,1) 26.8%, rgba(192,229,246,1) 64% );
        
    }

    form{
        border-radius: 5px;
        width: 60%;
        height: 500px;
        margin: auto;          
    }
   
    h1{
        color: rgb(2, 17, 153);
        text-align: center;
    }
    #header{
        border-radius: 5px;
        background-color: rgb(11, 170, 117);
        width: 40%;           
        margin: auto;
        padding: 5px 5px;
        box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;
    }
    #main{
     
      width: 100%;
      height: 100%;
      margin-top: 20px;
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      grid-template-rows: auto;
      padding: px;
    }
    #main>div{
        /* border: 2px solid rgb(6, 190, 21); */
        box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
        padding: 30px;
        margin: 5px;
        text-align: center;
        border-radius:  10px;
        font-size: x-large;
    }
    

    
</style>
</head>
<body  id="body">
    <div id="header"><h1>Welcome to Data Display Page</h1></div>
    <form>
        <div id="main">
            <div>User Name</div>
            <div>${username}</div>

            <div>Operating System</div>
            <div>${osname}</div>

            <div>Browser Name</div>
            <div>${browser}</div>

            <div>Lattitude</div>
            <div>${lattitude}</div>

            <div>Longitude</div>
            <div>${longitude}</div>

            <div>Login Time</div>
            <div>${logtime}</div>

            <div>Login Session</div>
            <div>${logsession}</div>
    
  </div>
        
    
	
	
</form>
	
</body>

</html>

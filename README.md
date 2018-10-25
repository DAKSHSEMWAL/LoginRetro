# LoginRetro

A Simple App To login Using Retrofit

* Add Base URL of your api


# My JSON Object format on sucessfull login

{

    "data": {
    
             "email": "client@gmail.com",
    
             "name": "Client",
    
             "phone": "",
    
             "location": "Kuwait",
    
             "city": "Kuwait",
    
             "profile": "1534923913-amlate.png",
   
             "token": "8db45ded412b3a9b6f30715bd7f96b0a",
   
             "id": "6",
   
              "business_name": ""
   
            },

           "status": true,

           "message": "Details Matched"
   
    }"
    
# Json Object if email is missing

    {

        "error": 
            {
                
                "email": "The email field is required."
            
            },
        
        "status": false,

        "message": "Details Not Matched"
    }
    
  # Json Object if password is missing

    {

        "error": 
            {
                
                "email": "The password field is required."
            
            },
        
        "status": false,

        "message": "Details Not Matched"
    }
    
 # Json Object if both field are missing

    {

        "error": 
            {
                
                "email": "The password field is required."
            
            },
        
        "status": false,

        "message": "Details Not Matched"
    }
# Json Object if Password is wrong
    {
 
        "status": false,
    
         "message": "Password Not Found"
    }   
    
 # Json Object if Email is wrong
    {
 
        "status": false,
    
         "message": "Email Not Found"
    }  

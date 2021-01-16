# webShop
In this application i made simple web shop using spring boot, MySQL database. For authorization and authentication i used spring security with JWT.

## <b>Description:</b>

In order to place order user must be registered. 

After registration user will recive email with link for activation.

After activation user can login (JWT expires in 10days).

User by default have role USER.

User can change password, address, phone number, see all orders, give comments for products, add products in cart, delete products from cart.

Admin users can add new products,add quantity for product, add new images for products, see all registered users, give role ADMIN to anyone, disable user, enable user.

Products can be returned as single product, products for particular category, latest products.

Every user can have only 1 open order. In order to finish buying user must confirm order and order details will be sent to users mail.

Uploaded images for products will be croped and saved on disk. Every image before saving is going through hash function and hash is saved in database with image path and other image details.

Every product have own directory for storing images.

When user wants to see images for product, image from disk goes again through hash function and if hashes are matched, image will be returned to user.

## AuthController(/api/auth)

This controller is available for anyone, you dont need to be registered to use it. 
Enpoints in this controllers are:

### /register (RequestMethod.POST)

This endpoint is used for registering new user in system. Requested body must be provided. 

    UserRegister(String username, 
                 String password, 
                 String email, 
                 String first_name, 
                 String last_name, 
                 String address, 
                 String phone_number)


After calling this endpoint you will recive email with link for activation your account.
In activation link token is generated and saved into database, user have 60min to activate, or token will expire and user will be deleted.
Password is stored in databse using BCrypt password encoder.



### /{registrationToken} (RequestMethod.POST)
This is the enpoint for previously recived email, token is check in database for that user, and if its same user will be activated.

    @PathVariable("registrationToken") String token
    
    
    
### /forgotPassword (RequestMethod.POST)
This endpoint is when user is forget his password, user need to provide his email as requested parameter: 

    @RequestParam(name = "userEmail", required = true) String userEmail
    
Email is checking in database, and if exist link with generated token will be sent on provided email, link is valid for 5min. After that time token will be deleted from database and user will need to call this endpoind again.



### /forgotPassword/{token} (RequestMethod.POST)
This endpoint is called with link sent to users email.

    @PathVariable("token") String token, @RequestParam(name = "newPassword", required = true) String newPassword, @RequestParam(name = "newPassword2", required = true) String newPassword2
    
First token is checking in database, if its ok, user will change his password with provided password, password will be encripted also with BCrypt password encoder.



## Admin Controller (/admin)
This controller is only for users who have role ADMIN, 


### /allUsers (RequestMethod.GET)
This endpoint returns all registered users as list of UserDTO which doesnt contain user passwords.

    @PageableDefault(page = 0, size = 3) Pageable pageable
    
User can provide pageable to filter how many users will be returned, by default is size 3.    



### /role/addAdmin/ (RequestMethod.POST)
This endpoint is used when ADMIN wants to give another user role ADMIN
    
    @RequestParam("email") String userEmail, @RequestParam("role") String role
    
Email and role are checking in database, if both are exist user with provided email will became ADMIN user.    



### /role/removeRole/ (RequestMethod.POST)
This endpoint removes role from user.
    
    @RequestParam("email") String userEmail, @RequestParam("role") String role
    
Email and role are checking in database, and if exist,, role provided in request param will be removed for user provided in request param email.



### /turnOffUser/{email} (RequestMethod.POST)
This endpoint allows ADMIN to disable user.

    @PathVariable("email") String email
    
Email is checking in database, and if exist user with provided email will be disabled.    



### /turnOnUser/{email} (RequestMethod.POST)
This endpoint allows ADMIN to enable user.

    @PathVariable("email") String email
    
Email is checking in database, and if exist user with provided email will be enabled. 



## User Controller (/user)

This controller is for user who have role USER,



### /changeAddress/{newAddress} (RequestMethod.POST)
Endpoint for user to change address.
    
    @PathVariable("newAddress") String newAddress, HttpServletRequest request
  
User needs to provide new address, and adress will be changed.



### /changePhoneNumber/{phoneNumber} (RequestMethod.POST)
Endpoint for user to change phone number.
    
    @PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request
  
User needs to provide new phone number, and number will be changed.



### /allOrders (RequestMethod.GET)
This endpoint return list of OrderDTO's, which is all completed orders for that user. 



### /allComments (RequestMethod.GET)
This endpoint return list of CommentDTO's, which is all comments for that user. 



## Order Controller (/order)

This controller can use only users who have roles ADMIN, USER.



### /confirmOrder (RequestMethod.POST)
This is endpoint to confirm order.

    @RequestBody(required = true) OrderDTO orderDTO
    
User need to provide OrderDTO. Provided order is checking in database, and if exist, email with details about order will be sent to user email. 



### /checkOrder (RequestMethod.GET)
Enpoint for checking user specific order.

    @RequestParam(name = "order_id") int order_id
    
User needs to provide order_id, if that order exist in database,user will recive OrderDTO with all informations about that particular order.    



### /deleteProduct (RequestMethod.POST)
Enpoind for deleting particular product from users unconfirmed order.

    @RequestParam(name = "product_id") int product_id
    
User need to provide product_id, and if that product exist in unconfirmed order, product will be deleted from that order and quantity of removed product will be returned to original product.   



## Product Controller (/product)

### /lastProducts (RequestMethod.GET)

This endpoint returns latest added products, and it can be called by anyone (doesn't need to be registered user).

    @PageableDefault(page = 0, size = 3, sort = "created", direction = Sort.Direction.ASC) Pageable pageable
    
It return pageable, where is by default set to size 3 and to be sorted by creation date.   



### /addProduct (RequestMethod.POST)
Endpoint where only ADMIN can add new products in databse.

    @RequestBody ProductDTO product
    ProductDTO(String name, 
               String description, 
               Double price, 
               Integer quantity, 
               String category)
               
User must provide ProductDTO in order to save new product in database.    



### /allCategories (RequestMethod.GET)
This endpoint returns all available categories from database.



### /category (RequestMethod.GET)
This return products based on category that user enter

    @RequestParam(name = "category") String category, @PageableDefault(page = 0, size = 3, sort = "created", direction = Sort.Direction.ASC) Pageable pageable
    
User must provide category, that category is checked in database and if exist returns page with latest added products in that category.
By default pageable is set to size 3 and sorted by creation date.



### /product (RequestMethod.GET)
This returns a product based on provided product_id

    @RequestParam(name = "product_id") int product_id
    
User must provide product_id, and if exist ind database ,product with that id will be returned to user as ProductDTO.     



### /addQuantity (RequestMethod.POST)
This endpoint allow ADMIN to add quantity for particular product.

    @RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "quantity") Integer quantity
    
User must provide product_id and quantity, and quantity will be added in that product.    



### /addComment (RequestMethod.POST)
This allows ADMIN or USER to add comment for product  

    @RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "comment") String comment
    
User must provide product_is and comment, and comment will be saved for that given product.



### /addToCart (RequestMethod.POST)
Endpoint for adding product into cart.

    @RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "quantity") Integer quantity
    
User provides product_id and quantity of that product. If user doesnt have any open order, the new order will be created and the product will be added.If user desire quantity is bigger than product quantity the exception will be thrown.
If user have open order, the product will be added into that order.



## Image File Controller (/image)

This controller is for handling images for products.The images will be saved on disk, file path of that image will be saved in database.
Every uploaded image will be croped and saved in temp directory, then image will be hashed and that hash will be saved in database.After that image will be copied in folder for product.When user wants images for some product, the image again goes trought hash algorithm and if hashes matches the image will be returned.



### /uploadImages (RequestMethod.POST)
This endpoint is for uploading images for products.

    @RequestParam(name = "images") MultipartFile[] images, @RequestParam(name = "product_id") Integer product_id
    
User must provide one or more images (MultipartFile) and also product id.Image is saved on disk, path and other details for that image will be saved in database.



### /getImage (RequestMethod.GET)
This endpoint return image for product.

    @RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "image_number") Integer image_number
    
User provides product_id and image_number. Image numbers are start from 1. Image is returned as Resource.      



## Login (/login) (RequestMethod.POST)
On this endpoint user can login into application.
  
    UserLogin(String email, String password)
    
User must provide email and password, if login is successful JWT is generated and expire in 10 days.    




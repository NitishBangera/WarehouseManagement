# Warehouse

Warehouse helps you organize your products into proper slots and provides a search which helps you find the products that you have put in the warehouse.

## Features
Warehouse works in three modes

1. Using an input file for initial upload.
2. Command line interface to execute the commands.
3. Web enpoint to execute the commands.

## Setup
### Softwares needed
1. Mariadb 10.3.10
2. Maven 3.5.2
3. Java8
### Database setup
1. CREATE DATABASE `warehouse`;
2. CREATE USER 'admin' IDENTIFIED BY 'admin';
3. GRANT USAGE ON *.* TO 'admin'@'%' IDENTIFIED BY 'admin';
4. GRANT ALL privileges ON `warehouse`.* TO 'admin'@'%';
5. FLUSH PRIVILEGES;

### Program Arguments
#### Mandatory
```sh
-Ddb.username=admin -Ddb.password=admin -Ddb.host=localhost -Ddb.port=3306 -Ddb.database=warehouse
```
#### Optional
```sh
-Dinput.file.path=<Absolute path of the file> -Dproduct.code.length=<product code size>
```

## Running the Application
```sh
$ cd warehouse
$ mvn spring-boot:run -Dmodule.version=0.0.1 <mandatory program arguments from above>
```

* The parameter input.file.path is optional. 
* If the input file isn't passed then the command line interface is enabled for taking in the commands.
* The web application is enabled by default and is ready to take in commands using the apis.

## Command line interface/Input file commands
| Command | Input parameters | Response | Description |
| ------ | ------ | ------ | ------ |
| warehouse <slot_number> | Number of slots | The warehouse id and the the number of slots that was created. | Creates a warehouse with the specified number of slots |
| warehouse use <warehouse_id> | Id of the warehouse | If the warehouse id is valid then changes the scope | Changes the scope to the warehouse for the further commands |
| store <product_code> <colour> | Product code and Colour | The slot number that was allocated | Stores the product with the specified product code and colour along with the internally allocated slot number |
| sell <slot_number> | Slot number | The slot number which was freed | Sells the product in the specified slot number |
| status |  | The warehouse slot information with the products | Shows the products stored in the warehouse |
| product_codes_for_products_with_colour <colour> | Color of the Product | Product codes of the products which matched the colour | Searches the products based on the color and displays the product codes |
| slot_numbers_for_products_with_colour <colour> | Color of the Product | Slot numbers of the products which matched the colour | Searches the products based on the color and displays the slot numbers |
| slot_number_for_product_code <product_code> | Product code | Slot number of the product which matched the product code | Searches the product based on the product code and displays the slot number |

## Web urls
| Api | Type | Input parameters | Response | Header | Description |
| ------ | ------ | ------ | ------ | ------ | ------ |
| /warehouse/{slot_number} | POST | Number of slots | The warehouse id and the the number of slots that was created. | | Creates a warehouse with the specified number of slots |
| /warehouse/use/{warehouse_id} | POST | Warehouse Id |If the warehouse id is valid then changes the scope. | x-auth-token | Changes the scope to the warehouse for the further commands. If the auth token header isn't passed then a new session is created |
| /store/{product_code}/{colour} | POST | Product code and Colour | The slot number that was allocated | x-auth-token | Stores the product with the specified product code and colour along with the internally allocated slot number |
| /sell/{slot_number} | POST | Slot number | The slot number which was freed | x-auth-token | Sells the product in the specified slot number |
| /status | GET | | The warehouse slot information with the products | x-auth-token | Shows the products stored in the warehouse |
| /search | POST | {"command" : "<search _commands>", "searchValue" : "<search_value>"} | Data for the search command and value | x-auth-token, Content-Type:application/json | Commands supported : product_codes_for_products_with_colour, slot_numbers_for_products_with_colour, slot_number_for_product_code |

x-auth-token is the session token which is unique and created on /warehouse and /warehouse/use.

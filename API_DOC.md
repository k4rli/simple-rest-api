# API Documentation

- [addCustomer](#addcustomer)
- [getCustomer](#getcustomer)
- [modifyCustomer](#modifycustomer)
- [removeCustomer](#removecustomer)


---

# addCustomer

Adds a new customer.

### Prerequisites

None.

### HTTP Request

```
POST /customer/addCustomer
```
### Request parameters

Do not supply a request parameters with this method.

### Optional request headers

Not needed.

### Request body

In the request body, provide the following query parameters with values.

| Parameter | Type | Description |
|:----------|:-----|:------------|
|name|String|Name of customer|
|address|String|Physical address|
|balance|Decimal|Balance (EUR)|
|birthday|String|Date of birth (dd.MM.yyyy)|

### Example

##### Request

```http
POST /customer/addCustomer
Content-Type: application/json
Request-Body:
```
```json
{
    "name": "Jaanus",
    "address": "Põhja 15",
    "balance": 58.05,
    "birthday": "12.01.1981"
}
```

##### Response

On success:
```http
HTTP/1.1 200 OK
Content-Type: application/json
Response-Body:
```
```json
{
  "result": "SUCCESS",
  "customer": {
     "id": 1,
     "name": "Jaanus",
     "address": "Põhja 15",
     "balance": 58.05,
     "birthday": "12.01.1981"
  }
}
```

---

# removeCustomer

Deletes a customer by ID.

### Prerequisites

Customer with ID provided must exist.

### HTTP Request

```
DELETE /customers/removeCustomer/{id}
```
### Request parameters

| Parameter | Type | Description |
|:----------|:-----|:------------|
|id|Integer|Customer ID|

### Optional request headers

Not needed.

### Request body

Do not supply a request body with this method.

### Example

##### Request

```http
DELETE /customer/removeCustomer/2
```

##### Response

On success:
```http
HTTP/1.1 200 OK
Content-Type: application/json
Response-Body: 
```
```json
{
  "result": "SUCCESS"
}
```

---

# getCustomer

Returns a customer by ID.

### Prerequisites

Customer with ID provided must exist.

### HTTP Request

```
GET /customers/getCustomer/{id}
```
### Request parameters

| Parameter | Type | Description |
|:----------|:-----|:------------|
|id|Integer|Customer ID|

### Optional request headers

Not needed.

### Request body

Do not supply a request body with this method.

### Example

##### Request

```http
GET /customer/getCustomer/2
```

##### Response

On success:
```http
HTTP/1.1 200 OK
Content-Type: application/json
Response-Body: 
```
```json
{
    "result": "SUCCESS",
    "customer": {
        "id": 2,
        "name": "Jaanus",
        "address": "Põhja 15",
        "balance": 58.05,
        "birthday": "12.01.1981"
    }
}
```

---

# modifyCustomer

Modifies fields on customer.

### Prerequisites

Customer with ID provided must exist.

### HTTP Request

```
PUT /customer/modifyCustomer
```
### Request parameters

Do not supply a request parameters with this method.

### Optional request headers

Not needed.

### Request body

In the request body, provide the following query parameters with values.

ID is the only required parameter. Provide additional parameters to modify customer data.

| Parameter | Type | Description |
|:----------|:-----|:------------|
|id|Integer|Customer ID|
|name|String|(OPTIONAL) Name of customer|
|address|String|(OPTIONAL) Physical address|
|balance|Decimal|(OPTIONAL) Balance (EUR)|
|birthday|String|(OPTIONAL) Date of birth (dd.MM.yyyy)|

### Example

##### Request

```http
PUT /customer/modifyCustomer?id=1&name=Peeter
```

##### Response

On success:
```http
HTTP/1.1 200 OK
Content-Type: application/json
Response-Body:
```
```json
{
  "result": "SUCCESS",
  "customer": {
     "id": 1,
     "name": "Peeter",
     "address": "Põhja 15",
     "balance": 58.05,
     "birthday": "12.01.1981"
  }
}
```
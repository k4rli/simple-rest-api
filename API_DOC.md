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
    "id": 1,
    "name": "Jaanus",
    "address": "Põhja 15",
    "balance": 58.05,
    "birthday": "12.01.1981"
}
```

---

# removeCustomer

Deletes a customer by ID.

### Prerequisites

None.

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
Content-Type: text/plain
Response-Body: "Customer with ID 2 has been deleted."
```

On error:
```http
HTTP/1.1 404 Not Found
Content-Type: text/plain
Response-Body: "Could not find customer by ID: 2"
```
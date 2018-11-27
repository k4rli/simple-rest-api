### POST /customers/addCustomer
**Request body:**  
```json
{
    "name": "Jaanus",
    "address": "Põhja 15",
    "balance": 58.05,
    "birthday": "12.01.1981"
}
```
**Response:**  

On success:
```json
{
    "id": 1,
    "name": "Jaanus",
    "address": "Põhja 15",
    "balance": 58.05,
    "birthday": "12.01.1981"
}
```
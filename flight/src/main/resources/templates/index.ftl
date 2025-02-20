<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flight Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; text-align: center; }
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f8f9fa; font-weight: bold; }
        form { display: inline-block; margin: 5px; }
        input, button { padding: 6px 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { cursor: pointer; }
        .delete { background-color: #d9534f; color: white; border: none; }
        .update { background-color: #f0ad4e; color: white; border: none; }
        .delete:hover, .update:hover { opacity: 0.8; }
        h1 { margin-top: 30px; }
    </style>
</head>
<body>

<h1>Add Flight</h1>
<form action="/create-flight" method="post">
    <input type="number" name="number" placeholder="Flight Number" required>
    <input type="text" name="destination" placeholder="Destination" required>
    <button type="submit">Add Flight</button>
</form>


<h1>Flight Details</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Number</th>
        <th>Destination</th>
        <th>Update Destination</th>
        <th>Add Passenger</th>
        <th></th>
    </tr>
    <#list flights as flight>
    <tr>
        <td>${flight.id}</td>
        <td>${flight.flight_number!''}</td>
        <td>${flight.destination!''}</td>
        <td>
            <form action="/update-destination" method="post">
                <input type="hidden" name="id" value="${flight.id}">
                <input type="text" name="new-dest" placeholder="New Destination" required>
                <button type="submit" class="update">Update</button>
            </form>
        </td>
        <td>
            <form action="/create-passenger" method="post">
                <input type="text" name="name" placeholder="Name" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type='hidden' name="flight_id" value="${flight.id}" required>
                <button type="submit">Add Passenger</button>
            </form>
        </td>
        <td>
            <form action="/delete-flight" method="post" onsubmit="return confirm('Are you sure?');">
                <input type="hidden" name="id" value="${flight.id}">
                <button type="submit" class="delete">Delete</button>
            </form>
        </td>
    </tr>
    </#list>
</table>

<h1>Passengers Details</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Flight Number</th>
        <th>Destination</th>
        <th></th>
    </tr>
    <#list passengers as passenger>
    <tr>
        <td>${passenger.id!''}</td>
        <td>${passenger.name!''}</td>
        <td>${passenger.email!''}</td>
        <td>${passenger.flight_number!''}</td>
        <td>${passenger.destination!''}</td>
        <td>
            <form action="/delete-passenger" method="post" onsubmit="return confirm('Are you sure?');">
                <input type="hidden" name="id" value="${passenger.id}">
                <button type="submit" class="delete">Delete</button>
            </form>
        </td>
    </tr>
    </#list>
</table>



</body>
</html>

<?php

	$cusName = $_GET['custName'];
	$phone = $_GET['phone'];
	
	$conn2 = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}

	$result = mysqli_query($conn2,"SELECT * FROM tblCustomer WHERE cusName = '" . $cusName . "' and cusPhone = '" . $phone . "'");

	if(mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_array($result))
		{
			$res = $res . $row['cusID'] . "%" . $row['cusName'] . "%" . $row['cusAddr'] . "%" . $row['cusPhone'] . "%" ;
		}
		echo $res;
	}
	else
		echo "nosuchuser";
	
	mysqli_close($conn2);
?>
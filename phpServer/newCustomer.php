<?php

	$cusName = $_GET['name'];
	$cusPwd = $_GET['pwd'];
	$cusAddr = $_GET['addr'];
	$cusPhone = $_GET['phone'];
	
	$conn2 = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}
	
	try
	{
		$result = mysqli_query($conn2,"INSERT INTO tblCustomer(cusName,cusPwd,cusAddr,cusPhone) VALUES ('" . $cusName . "','" . $cusPwd . "','" . $cusAddr . "','" . $cusPhone . "')");
		
		$res = mysqli_query($conn2,"SELECT MAX(cusID) as cID FROM tblCustomer");
		while($row = mysqli_fetch_array($res))
			$nID = $nID . $row['cID'];
		
		echo "addedID=" . $nID;
	}
	catch(Exception $ex)
	{
		echo "error";
	}
	mysqli_close($conn2);
?>
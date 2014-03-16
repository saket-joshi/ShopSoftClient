<?php

	$shopName = $_GET['sName'];
	$cusID = $_GET['cID'];
	$msgText = $_GET['msgText'];

	$conn = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}

	$sRes = mysqli_query($conn,"SELECT ShopID as sID FROM tblShop WHERE ShopName = '" . $shopName . "'");
	while($sRow = mysqli_fetch_array($sRes))
		$shopID = $sRow['sID'];

	$result = mysqli_query($conn,"INSERT INTO tblChats(shopID,cusID,fromShop,msgText) VALUES ( " . $shopID . "," . $cusID . ",1,'" . $msgText . "')");
	
	$res = mysqli_query($conn,"SELECT MAX(msgID) as maxID FROM tblChats");
	while($row = mysqli_fetch_array($res))
	{
		$msgID = $msgID . $row['maxID'];
	}
	
	echo "sentmessageID%" . $msgID;
	
	mysqli_close($conn);
?>
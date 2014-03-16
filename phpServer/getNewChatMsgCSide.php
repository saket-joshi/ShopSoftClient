<?php

	$shopName = $_GET['sName'];
	$cusID = $_GET['cID'];
	$prevMsg = $_GET['prevID'];

	$conn = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}
	
	$sRes = mysqli_query($conn,"SELECT ShopID as sID FROM tblShop WHERE ShopName = '" . $shopName . "'");
	while($sRow = mysqli_fetch_array($sRes))
		$shopID = $sRow['sID'];
	
	$result = mysqli_query($conn,"SELECT MAX(msgID) as maxID FROM tblChats WHERE shopID = " . $shopID . " AND cusID = " . $cusID . " AND fromShop = 1");
	try
	{
		while($nRow = mysqli_fetch_array($result))
		{
			$maxMsgID = $nRow['maxID'];
		}
		if($maxMsgID > $prevMsg || $prevMsg == 0)
		{
			$res = mysqli_query($conn,"SELECT * FROM tblChats WHERE msgID = " . $maxMsgID);
			while($row = mysqli_fetch_array($res))
			{
				$msgText = $msgText . $row['msgText'];
			}
			echo $msgText;
		}
		else
		{
			echo "nonewmsgs";
		}
	}
	catch(Exception $ex)
	{
		echo "nonewmsgs";
	}
		
	mysqli_close($conn);
?>
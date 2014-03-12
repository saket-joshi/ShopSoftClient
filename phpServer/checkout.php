<?php

	$shopName = $_GET['shopName'];
	$custID = $_GET['customerID'];
	$total = $_GET['billTotal'];
	$prodArr = $_GET['prodArr'];
	$qtyArr = $_GET['prodQty'];

	$arrName = split("%", $prodArr);
	$arrQty = split("%", $qtyArr);

	$conn2 = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}

	$result2 = mysqli_query($conn2,"SELECT * FROM tblShop WHERE ShopName = '" . $shopName . "'");
	while($row = mysqli_fetch_array($result2))
	{
		$resultAdmin = $row['DbUser'];
		$resultAddr = $row['DbAddress'];
		$resultPwd = $row['DbPassword'];
		$hostname = $row['hostname'];
	}

	mysqli_close($conn2);

	$conn = mysqli_connect($hostname,$resultAdmin,$resultPwd,$resultAddr);
	if(mysqli_connect_errno())
	{
		echo "Failed to connect to the server";
	}
	
	try
	{
		$addtobill = mysqli_query($conn,"INSERT INTO tblBill(CustomerID,DateofBill,Total) VALUES (" . $custID . ",'" . date("d-m-Y") . "',0)");
		$currBillNo = mysqli_query($conn,"SELECT MAX(BillID) as maxID FROM tblBill");
		$nRow = mysqli_fetch_array($currBillNo);
		$billNo = $nRow['maxID'];

		for($i=0;$i<sizeof($arrName) - 1;$i++)
		{
			$getProd = mysqli_query($conn,"SELECT ProductID as prodID FROM tblProductDetails WHERE ProdName = '" . $arrName[$i] . "'");
			$row = mysqli_fetch_array($getProd);
			$resProd = $row['prodID'];
			$billDetails = mysqli_query($conn,"INSERT INTO tblBillDetails VALUES (" . $billNo . "," . $resProd . "," . $arrQty[$i] . ")");
			$updStock = mysqli_query($conn,"UPDATE tblProductDetails SET Stock = Stock - " . $arrQty[$i] . " WHERE ProductID = " . $resProd . ")");
		}
		
		$updateTotal = mysqli_query($conn,"UPDATE tblBill SET Total = " . $total . " WHERE BillID = " . $billNo);
		$setUndelivered = mysqli_query($conn,"UPDATE tblBill SET Delivered = -1 WHERE BillID = " . $billNo);
		echo 1;
	}
	
	catch(Exception $ex)
	{
		echo $ex->getMessage();
	}

?>
<?php

	$prodName = $_GET['productName'];
	$shopName = $_GET['shopName'];

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

	$result = mysqli_query($conn,"SELECT * FROM tblProductDetails WHERE ProdName = '" . $prodName . "'");

	while($row = mysqli_fetch_array($result))
	{
		$prodMRP = $row['MRP'];
		$prodRate = $row['Rate'];
		$prodInfo = $row['Information'];
		$prodStock = $row['Stock'];
		$prodWarranty = $row['Warranty'];
		$prodImageURL = $row['Photos'];
	}
	
	$resultStr = $prodMRP . "%" . $prodRate . "%" . $prodInfo . "%" . $prodStock . "%" . $prodWarranty . "%" . $prodImageURL . "%";
	echo $resultStr;

?>		
<?php

$shopName = $_GET['shopName'];
$billNo = $_GET['billNo'];

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
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
}

try
{
	$result = mysqli_query($conn,"UPDATE tblBill SET Delivered = 1 WHERE BillID = " . $billNo);
	echo "set";
}
catch(Exception $ex)
{
	echo "error";
}

mysqli_close($conn);

?>
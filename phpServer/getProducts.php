<?php

$subCat = $_GET['subCategory'];
$shopName = $_GET['shopName'];

$conn2 = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
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

$result = mysqli_query($conn,"SELECT ProdName FROM tblProductDetails WHERE ProductID IN (SELECT ProductID FROM tblProducts WHERE SubCatID IN (SELECT SubCatID FROM tblSubCat WHERE SubCatName = '" . $subCat . "'))");

while($row = mysqli_fetch_array($result))
{
	$resultStr = $resultStr . $row['ProdName'] . "%";
}
 
mysqli_close($conn);
echo $resultStr;
 
?>
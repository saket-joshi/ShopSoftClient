<?php

$categoryName = $_GET['categoryName'];
$shopName = $_GET['shopName'];

$conn2 = mysqli_connect("mysql9.000webhost.com","a4921234_sserver","shopsoft123","a4921234_server");
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

$result = mysqli_query($conn,"SELECT * FROM tblSubCat WHERE CatID = (SELECT CatID FROM tblCategory WHERE CatName = '" . $categoryName . "')");
while($row = mysqli_fetch_array($result))
{
	$resultStr = $resultStr . $row['SubCatName'] . "%";
}

mysqli_close($conn);
echo $resultStr;

?>

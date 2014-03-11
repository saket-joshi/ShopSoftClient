<?php

$shopName = $_GET['shopName'];

$conn = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
}
 
$result = mysqli_query($conn,"SELECT * FROM tblShop WHERE ShopName = '" . $shopName . "'");
while($row = mysqli_fetch_array($result))
{
	$resultAdmin = $row['DbUser'];
	$resultStr = $row['DbAddress'];
	$resultPwd = $row['DbPassword'];
	$hostname = $row['hostname'];
}
mysqli_close($conn);

$conn2 = mysqli_connect($hostname,$resultAdmin,$resultPwd,$resultStr);
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
}

$result2 = mysqli_query($conn2,"SELECT * FROM tblCategory");

while($row2 = mysqli_fetch_array($result2))
{
	$resultStr2 = $resultStr2 . $row2['CatName'] . "%";
}
 
echo $resultStr2;
mysqli_close($conn2);

?>		
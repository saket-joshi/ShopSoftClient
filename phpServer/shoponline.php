<?php

$shopName = $_GET['shopName'];

$conn = mysqli_connect("mysql9.000webhost.com","a4921234_sserver","shopsoft123","a4921234_server");
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
}
 
$result = mysqli_query($conn,"SELECT Status FROM tblShop WHERE ShopName = '" . $shopName . "'");
while($row = mysqli_fetch_array($result))
{
	$resultStatus = $row['Status'];
}
mysqli_close($conn);

echo $resultStatus;

?>	
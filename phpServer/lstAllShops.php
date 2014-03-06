<?php

$conn = mysqli_connect("mysql9.000webhost.com","a4921234_sserver","shopsoft123","a4921234_server");
if (mysqli_connect_errno())
{
	echo "Failed to connect to mysql";
}

$result = mysqli_query($conn,"SELECT ShopName FROM tblShop");
while($row = mysqli_fetch_array($result))
{
	$resultStr = $resultStr . $row['ShopName'] . '%' ;
}

echo $resultStr;
mysqli_close($conn);

?>
<?php

$conn = mysqli_connect("localhost","syntaxso_sserver","shopsoft123","syntaxso_server");
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
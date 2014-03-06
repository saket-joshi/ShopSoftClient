<?php

$categoryName = $_GET['categoryName'];

$conn = mysqli_connect("mysql9.000webhost.com","a4921234_emadmin","eternity123","a4921234_dbEMall");
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

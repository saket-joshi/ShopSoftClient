<?php

$subCat = $_GET['subCategory'];

$conn = mysqli_connect("mysql9.000webhost.com","a4921234_emadmin","eternity123","a4921234_dbEMall");
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
<?php

header('Content-Type: application/json');

$data = [
    'ghgEmissionCount' => (time() % 10000) * 3
];  // replace with database call - calculate formula

echo json_encode($data);
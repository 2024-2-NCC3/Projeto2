var express = require("express");
var app = express();
var port = process.env.PORT || 3000;

var bodyParser = require("body-parser");
var mysql = require("mysql");

var db = mysql.createConnection({
  host: "projeto2semestre.mysql.database.azure.com",
  user: "projetoMysql",
  password: "@mw2302ee",
  database: "projetoMobile",
});

db.connect((err) => {
  if (err) {
    console.error("Error connecting to the database: " + err.stack);
    return;
  }
  console.log("Connected to MySQL database as ID " + db.threadId);
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post("/complaint", (req, res) => {
  console.log("Request body:", req.body); // Log do conteúdo recebido

  const { type, report } = req.body;
  if (!type || !report) {
    return res.status(400).send("Incomplete data.");
  }

  const query =
    "INSERT INTO complaintScreen (about, caseDescription) VALUES (?, ?)";
  db.query(query, [type, report], (err, result) => {
    if (err) {
      console.error("Error inserting complaint:", err); // Log em caso de erro
      return res.status(500).send("Server error");
    }
    console.log("Complaint inserted successfully:", result); // Log de sucesso
    res.status(200).send("Complaint submitted successfully.");
  });
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

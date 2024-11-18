var express = require("express");
var app = express();
var port = process.env.PORT || 3000;

var bodyParser = require("body-parser");
var nodemailer = require("nodemailer");
var mysql = require("mysql");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "satirog719@gmail.com",
    pass: "allx jqwq aant ivwa",
  },
});

var db = mysql.createConnection({
  host: "projeto2semestre.mysql.database.azure.com",
  user: "projetoMysql",
  password: "@mw2302ee",
  database: "projetoMobile",
});

db.connect((err) => {
  if (err) {
    console.error("Erro ao conectar ao banco de dados: " + err.stack);
    return;
  }
  console.log("Conectado ao banco de dados MySQL com o ID " + db.threadId);
});

app.post("/complaint", (req, res) => {
  console.log("Request body:", req.body);

  const { type, report, region } = req.body;

  if (!type || !report || !region) {
    return res.status(400).send("Dados incompletos");
  }

  var mailOptions = {
    from: "satirog719@gmail.com",
    to: "satirog71@gmail.com",
    subject: "Nova Denúncia Recebida",
    text: `Denúncia recebida:

Tipo: ${type}
Descrição: ${report}
Região: ${region}`,
  };

  transporter.sendMail(mailOptions, (err, info) => {
    if (err) {
      console.error("Erro ao enviar o e-mail:", err);
      return res.status(500).send("Erro ao enviar e-mail");
    }
    console.log("E-mail enviado:", info.response);

    // Inserir a região no banco de dados
    const query = "INSERT INTO complaintScreen (region) VALUES (?)";
    db.query(query, [region], (err, result) => {
      if (err) {
        console.error("Erro ao inserir denúncia:", err);
        return res
          .status(500)
          .send("Erro ao registrar a denúncia no banco de dados");
      }
      console.log("Denúncia registrada com sucesso:", result);
      res
        .status(200)
        .send("Denúncia enviada com sucesso e registrada no banco de dados.");
    });
  });
});

app.listen(port, () => {
  console.log(`Servidor está rodando na porta ${port}`);
});

app.get("/locations", (req, res) => {
  const query = "SELECT region FROM complaintScreen";
  db.query(query, (err, results) => {
    if (err) {
      console.error("Erro ao buscar regiões:", err);
      return res.status(500).send("Erro no servidor");
    }
    res.json(results);
  });

  app.get("/mostFrequent", (req, res) => {
    const query =
      "select region, count(region) as most_Frequent from complaintScreen group by region order by count(region) desc ";
    db.query(query, (err, result) => {
      if (err) {
        console.error("Erro ao buscar mais frequente:", err);
        return res.status(500).send("Erro no servidor");
      }
      res.json(result);
    });
  });
});

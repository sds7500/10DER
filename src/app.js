require('dotenv').config();

const express = require('express');
const app=express()
const path = require('path');
const hbs = require('hbs');
require("./db/conn")
const Register = require('./models/registers');
const bcrypt = require('bcryptjs');

const PORT=process.env.PORT||3000;

app.use(express.json())
app.use(express.urlencoded({extended:false}))

app.use(express.static(path.join(__dirname,"../public")))

app.set('view engine','hbs');
app.set('views','../templates/views')
hbs.registerPartials('../templates/partials')

console.log(process.env.SECRET_KEY);

//create new account of user
app.post('/login',async(req,res)=>{
    try{
        const pwd=req.body.sign_password;
        const cpwd=req.body.sign_cpassword;
        if(pwd===cpwd){
            const registerUser=new Register({
                name:req.body.sign_name ,
                email:req.body.sign_email ,
                Phone:req.body.sign_mobileno ,
                password:req.body.sign_password
            })

            const token=await registerUser.genrateAuthToken();
            const registered=await registerUser.save();
            res.status(201).render('index')
        }
    }
    catch(error){
        res.status(400).send(error)
    }
})

//logging in existing user

app.post('/enter',async(req,res)=>{
    try{
        const name=req.body.login_name;
        const pwd=req.body.login_password;
        
        const username=await Register.findOne({name:req.body.login_name})
        
        const isMatch=await bcrypt.compare(pwd,username.password)
        const token=await username.genrateAuthToken();
        const registered=await username.save();

        if(isMatch){
            res.status(201).render('client');
        }
    }
    catch(err){
        res.status(400).send(err)
    }
})

app.get("/",(req,res)=>{
    res.render('index')
})

app.get("/login",(req,res)=>{
    res.render('login')
})

app.listen(PORT,()=>{
    console.log(`server is running at port no :${PORT}`);
})
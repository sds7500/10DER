require('dotenv').config();
const express = require('express');
const app=express()
const path = require('path');
const hbs = require('hbs');
require("./db/conn")
const Register = require('./models/registers');
const bcrypt = require('bcryptjs');
const cookieParser = require('cookie-parser');
const auth = require('./middleware/auth');

const PORT=process.env.PORT||3000;

app.use(express.json())
app.use(express.urlencoded({extended:false}))
app.use(cookieParser())

app.use(express.static(path.join(__dirname,"../public")))

app.set('view engine','hbs');
app.set('views','../templates/views')
hbs.registerPartials('../templates/partials')

//create new account of user
app.post('/login',async(req,res)=>{
    try{
        const pwd=req.body.sign_password;
        const cpwd=req.body.sign_cpassword;
        const name=req.body.sign_name
        if(pwd===cpwd){
            const registerUser=new Register({
                name:req.body.sign_name ,
                email:req.body.sign_email ,
                Phone:req.body.sign_mobileno ,
                password:req.body.sign_password
            })
            const token=await registerUser.genrateAuthToken();
            res.cookie("jwt",token)
            //optional field after token    expires:new Date(Date.now()+300000000)
            const registered=await registerUser.save();
            res.status(201).render('index',{
                login:'Login',
                register:'Register',
                login_h:'/login',
                register_h:'/login'
            })
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
        
        const username=await Register.findOne({$or :[{email:name},{Phone:name}]})
        
        const isMatch=await bcrypt.compare(pwd,username.password)
        const token=await username.genrateAuthToken();

        res.cookie("jwt",token,{
            expires:new Date(Date.now()+5000000),
            httpOnly:true
        });
        const registered=await username.save();

        if(isMatch){
            res.status(201).render('index',{
                login:name,
                register:'logout',
                login_h:'/cart',
                register_h:'/logout'
            });
        }
    }
    catch(err){
        res.status(400).send(err)
    }
})

app.get("/",(req,res)=>{
    res.render('index',{
        login:'Login',
        register:'Register',
        login_h:'/login',
        register_h:'/login'
    })
})

app.get("/login",(req,res)=>{
    res.render('login')
})

app.get('/cart',auth,(req,res)=>{
    res.render('cart');
})

app.get('/logout',auth,async(req,res)=>{
    try{
        req.user.tokens=req.user.tokens.filter((curr)=>{
            return curr.token!=req.token
        })
        res.clearCookie('jwt');
        await req.user.save();
        res.render("login");
    }
   catch(err){
       res.status(500).send(err)
   }
})

app.listen(PORT,()=>{
    console.log(`server is running at port no :${PORT}`);
})
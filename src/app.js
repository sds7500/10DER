require('dotenv').config();
const express = require('express');
const app=express()
const path = require('path');
const hbs = require('hbs');
require("./db/conn")
const Register = require('./models/registers');
const Tender=require('./models/tender')
const bcrypt = require('bcryptjs');
const cookieParser = require('cookie-parser');
const auth = require('./middleware/auth');
const jwt = require('jsonwebtoken');

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
            httpOnly:true
        });
        const registered=await username.save();

        if(isMatch){
            res.status(201).render('index',{
                login:username.name,
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

app.post('/createTender',auth,async(req,res)=>{
    try{
        const registerTender=new Tender({
            Username: req.user._id,
            Title:req.body.title ,
            Description:req.body.description ,
            Category:req.body.category,
            Start_Date:Date.now(),
            Start_date:new Date().toISOString().slice(0, 10),
            End_Date:req.body.end_date
        })
        const registered=await registerTender.save();
        res.status(201).render('cart');
    }
    catch(error){
        res.status(400).send(error);
    }
})

app.get("/",async(req,res)=>{
    const token=req.cookies.jwt;
    if(typeof token === 'undefined'){
        res.render('index',{
            login:'Login',
            register:'Register',
            login_h:'/login',
            register_h:'/login'
        })
    }
    else{
        const verifyUser=jwt.verify(token,process.env.SECRET_KEY);
        const user=await Register.findOne({_id:verifyUser._id})
        res.render('index',{
            login:user.name,
            register:'logout',
            login_h:'/cart',
            register_h:'/logout'
        })
    }
})

app.get("/login",(req,res)=>{
    res.render('login')
})

app.get('/cart',auth,(req,res)=>{
    res.render('cart');
})

app.get('/atender',auth,async(req,res)=>{
    const result=await Tender.find({$and :[{Username:req.user._id},{End_Date : {$gte:Date.now()}}]});
    
    res.render('atender',{
        Tender:result
    })
})

app.get('/ctender',auth,async(req,res)=>{
    const result=await Tender.find({$and :[{Username:req.user._id},{End_Date : {$lt:Date.now()}}]});
    
    res.render('ctender',{
        Tender:result
    })
})

app.get('/contact',(req,res)=>{
    res.render('contact');
})

app.get('/team',(req,res)=>{
    res.render('team');
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
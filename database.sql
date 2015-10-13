

db.createUser(
  {
    user: "jack",
    pwd: "1313",
    roles:
    [
      {
        role: "userAdminAnyDatabase",
        db: "admin"
      }
    ]
  }
)


db.runCommand(
  {
    usersInfo:"jack",
    showPrivileges:true
  }
)






use doctorapp

mongo  111.1.76.108/doctorapp -u jack -p 1313
mongo  111.1.76.108/mobileapp -u jack -p 1313

db.products.update(
   { _id: 100 },
   { $set: { "details.make": "zzz" } },
   false,true
)


db.createUser(
  {
    user:"jack",
    pwd:"1313",
    roles:[
      {role:"userAdminAnyDatabase",db:"mobileapp"}
    ]
  }
)

db.createUser( { "user" : "jack",
                 "pwd": "1313",

                 "roles" : [ { role: "clusterAdmin", db: "admin" },
                             { role: "readAnyDatabase", db: "admin" },
                             "readWrite"
                             ] },
               { w: "majority" , wtimeout: 5000 } )


show dbs
show collections








--用户表

db.users.insert({
username : "jack",
realname : "王小明",
password:"1"
})

--急救类型模板表
db.caretemplists.insert(
{ title: '中风', content:{checklist:[
        { text: "处置1",data:[{ text: "挂盐水", checked: true},
            { text: "打针", checked: false  },
            { text: "输氧", checked: false }]},
        { text: "处置2",data:[{ text: "挂盐水", checked: true  },
            { text: "打针", checked: false  },
            { text: "输氧", checked: false  }]},
        { text: "处置3",data:[{ text: "挂盐水", checked: true },
            { text: "打针", checked: false},
            { text: "输氧", checked: false}]}
    ],
    options:""
    }
    }
)
--急救记录表
db.carerecordlists.insert(
{ username:'王小明',cardno:'1212121', tempcontent:{checklist:[
        { text: "处置1",data:[{ text: "挂盐水", checked: true},
            { text: "打针", checked: false  },
            { text: "输氧", checked: false }]},
        { text: "处置2",data:[{ text: "挂盐水", checked: true  },
            { text: "打针", checked: false  },
            { text: "输氧", checked: false  }]},
        { text: "处置3",data:[{ text: "挂盐水", checked: true },
            { text: "打针", checked: false},
            { text: "输氧", checked: false}]}
    ],
    options:""
    },caredetail:{},time:new Date()
    }
)


--消息表维护 (1 doctor,0 patient)

db.messages.insert(
   {
	      fromid: "551b4cb83b83719a9aba9c01",
        toid:"551b4e1d31ad8b836c655377",
        fromtype:1,
        totype:1,
        msgtime:new Date(),
        content:"hello jack",
        isread:false
   }
)













"use strict";(self.webpackChunkfinal3=self.webpackChunkfinal3||[]).push([[317],{6561:(e,s,a)=>{a.d(s,{A:()=>n});var l=a(579);const n=function(e){return(0,l.jsx)(l.Fragment,{children:(0,l.jsx)("div",{className:"row",children:(0,l.jsx)("div",{className:"col",children:(0,l.jsxs)("div",{style:{backgroundColor:"#FFC0CB"},className:"p-4 text-light rounded",children:[(0,l.jsx)("h1",{children:e.title}),(0,l.jsx)("p",{children:e.content})]})})})})}},2317:(e,s,a)=>{a.r(s),a.d(s,{default:()=>o});var l=a(5043),n=(a(6561),a(831)),t=a(9005),c=a(7580),i=a(3216),r=(a(9765),a(5630)),d=a(579);const o=()=>{const[e,s]=(0,l.useState)({id:"",pw:""}),[a,o]=(0,n.L4)(t.g3),[m,h]=(0,n.L4)(t.Xv),[x,j]=(0,n.L4)(t.cX),[p,g]=(0,n.L4)(t.VB),u=(0,l.useCallback)((a=>{s({...e,[a.target.name]:a.target.value})}),[e]),w=(0,i.Zp)(),v=(0,l.useCallback)((async()=>{if(0!==e.id.length&&0!==e.pw.length){try{const s=await c.A.post("/admin/login",e);o(parseInt(s.data.loginId)),h(s.data.loginLevel),j(s.data.isPaid),g(s.data.isChecked),c.A.defaults.headers.common.Authorization=s.data.accessToken,window.localStorage.setItem("refreshToken",s.data.refreshToken)}catch(s){!s.response||401!==s.response.status&&404!==s.response.status?window.alert("\ub85c\uadf8\uc778 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4:",s.message):window.alert("\uc544\uc774\ub514 \ud639\uc740 \ube44\ubc00\ubc88\ud638\uac00 \uc77c\uce58\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4")}w("/admin/home")}}),[e]);return(0,d.jsx)(d.Fragment,{children:(0,d.jsx)("div",{className:"container login-container",children:(0,d.jsx)("div",{className:"row",children:(0,d.jsx)("div",{className:"col-6 offset-3",children:(0,d.jsxs)("div",{className:"login-wrapper w-100",children:[(0,d.jsx)("div",{className:"img-wrapper text-center mb-4",children:(0,d.jsx)("img",{className:"logo-image",src:r,alt:"Logo"})}),(0,d.jsx)("div",{className:"row mt-4",children:(0,d.jsxs)("div",{className:"col",children:[(0,d.jsx)("label",{children:"\uc6b4\uc601\uc790 \uc544\uc774\ub514"}),(0,d.jsx)("input",{type:"text",name:"id",className:"form-control",value:e.id,onChange:e=>u(e)})]})}),(0,d.jsx)("div",{className:"row mt-4",children:(0,d.jsxs)("div",{className:"col",children:[(0,d.jsx)("label",{children:"\ube44\ubc00\ubc88\ud638"}),(0,d.jsx)("input",{type:"password",name:"pw",className:"form-control",value:e.pw,onChange:e=>u(e)})]})}),(0,d.jsx)("div",{className:"row mt-4",children:(0,d.jsx)("div",{className:"col",children:(0,d.jsx)("button",{className:"btn btn-dark w-100",onClick:e=>v(),children:"\ub85c\uadf8\uc778"})})})]})})})})})}},9765:()=>{}}]);
//# sourceMappingURL=317.ee02246b.chunk.js.map
"use strict";(self.webpackChunkfinal3=self.webpackChunkfinal3||[]).push([[366],{6561:(e,t,a)=>{a.d(t,{A:()=>o});var s=a(579);const o=function(e){return(0,s.jsx)(s.Fragment,{children:(0,s.jsx)("div",{className:"row",children:(0,s.jsx)("div",{className:"col",children:(0,s.jsxs)("div",{style:{backgroundColor:"#FFC0CB"},className:"p-4 text-light rounded",children:[(0,s.jsx)("h1",{children:e.title}),(0,s.jsx)("p",{children:e.content})]})})})})}},7366:(e,t,a)=>{a.r(t),a.d(t,{default:()=>u});var s=a(5043),o=a(9005),r=a(831),c=a(6561),l=a(184),i=a(423),n=a(397),d=a(7580),m=a(8839),p=a(5475),j=a(6058),x=a(579);const u=()=>{const[e,t]=(0,r.L4)(o.g3),[a,u]=(0,r.L4)(o.Xv),[h,N]=(0,s.useState)([]),[b,g]=(0,s.useState)([]),[k,v]=(0,s.useState)([]),[C,y]=(0,s.useState)([]),[f,w]=(0,s.useState)({projectName:"",projectStartTime:"",projectLimitTime:"",empNo:"",empName:"",deptName:"",gradeName:""}),[L,S]=(0,s.useState)(null);(0,s.useEffect)((()=>{T()}),[]);const T=(0,s.useCallback)((async()=>{const t=e,a=await d.A.get("/project/"+t);N(a.data)}),[]),B=((0,s.useCallback)((async e=>{if(!1===window.confirm("\uc815\ub9d0 \uc0ad\uc81c\ud558\uc2dc\uaca0\uc2b5\ub2c8\uae4c?"))return;await d.A.delete("/project/"+e.projectNo);T()}),[h]),(0,s.useCallback)((e=>{w({...f,[e.target.name]:e.target.value})}),[f])),F=(0,s.useCallback)((async()=>{await d.A.post("/project/",f);T(),M(),D()}),[f]),A=(0,s.useCallback)((()=>{!1!==window.confirm("\uc791\uc131\uc744 \ucde8\uc18c\ud558\uc2dc\uaca0\uc2b5\ub2c8\uae4c?")&&(M(),D())}),[f]),M=(0,s.useCallback)((()=>{w({projectName:"",projectStartTime:"",projectLimitTime:"",empNo:"",empName:""})}),[f]),E=((0,s.useCallback)((e=>{const t=[...h].map((e=>!0===e.edit?{...L,edit:!1}:{...e}));S({...e});const a=t.map((t=>e.projectNo===t.projectNo?{...t,edit:!0}:{...t}));N(a)}),[h]),(0,s.useCallback)((e=>{const t=[...h].map((t=>e.projectNo===t.projectNo?{...L,edit:!1}:{...t}));N(t)}),[h])),R=(0,s.useCallback)(((e,t)=>{const a=[...h].map((a=>t.projectNo===a.projectNo?{...a,[e.target.name]:e.target.value}:{...a}));N(a)}),[h]),I=(0,s.useCallback)((async e=>{await d.A.patch("/project/",e);T()}),[h]),q=(0,s.useRef)(),z=(0,s.useCallback)((()=>{const t=new m.aF(q.current);w({...f,empNo:e}),t.show()}),[q]),D=(0,s.useCallback)((()=>{m.aF.getInstance(q.current).hide()}),[q]);return(0,x.jsxs)(x.Fragment,{children:[(0,x.jsx)(c.A,{title:"\ub0b4 \ud504\ub85c\uc81d\ud2b8"}),(0,x.jsx)("div",{className:"row mt-4",children:(0,x.jsx)("div",{className:"col text-end",children:(0,x.jsxs)("button",{className:"btn btn-primary",onClick:z,style:{backgroundColor:"pink",border:"none",transition:"background-color 0.3s, color 0.3s",cursor:"pointer"},onMouseEnter:e=>{e.target.style.backgroundColor="hotpink",e.target.style.color="white"},onMouseLeave:e=>{e.target.style.backgroundColor="pink",e.target.style.color="inherit"},children:[(0,x.jsx)(i.t50,{})," \uc0c8 \ud504\ub85c\uc81d\ud2b8"]})})}),h.map((e=>(0,x.jsx)("div",{className:"row mt-4",children:(0,x.jsx)("div",{className:"col",children:(0,x.jsx)("div",{className:"card mb-3",style:{border:"2px solid pink"},children:(0,x.jsxs)("div",{className:"card-body",style:{borderBottom:"2px solid pink"},children:[(0,x.jsxs)("div",{className:"card-title d-flex align-items-center",children:[(0,x.jsx)(j.NVk,{style:{color:"#007bff",fontSize:"1.5em",marginRight:"0.5em"}}),e.edit?(0,x.jsx)("input",{type:"text",value:e.projectName,name:"projectName",onChange:t=>R(t,e),className:"form-control"}):(0,x.jsx)(p.N_,{to:"/document/project/".concat(e.projectNo),style:{textDecoration:"none",color:"inherit"},children:(0,x.jsx)("span",{style:{backgroundColor:"pink",padding:"5px 10px",borderRadius:"5px",transition:"background-color 0.3s, color 0.3s",cursor:"pointer"},onMouseEnter:e=>{e.target.style.backgroundColor="hotpink",e.target.style.color="white"},onMouseLeave:e=>{e.target.style.backgroundColor="pink",e.target.style.color="inherit"},children:e.projectName})})]}),(0,x.jsxs)("div",{className:"card-text",style:{marginLeft:"5px",borderBottom:"2px solid pink",marginBottom:"5px",padding:"10px 0"},children:["\ud504\ub85c\uc81d\ud2b8 \ubc88\ud638: ",e.projectNo]}),(0,x.jsxs)("div",{className:"card-text",style:{marginLeft:"5px",borderBottom:"2px solid pink",marginBottom:"5px",padding:"10px 0"},children:["\uc791\uc131\uc790: ",e.projectWriter]}),(0,x.jsxs)("div",{className:"card-date-picker",style:{marginLeft:"5px",borderBottom:"2px solid pink",marginBottom:"5px",padding:"10px 0"},children:["\uc2dc\uc791\uc77c: ",e.edit?(0,x.jsx)("input",{type:"date",name:"projectStartTime",value:e.projectStartTime,onChange:t=>R(t,e)}):e.projectStartTime]}),(0,x.jsxs)("div",{className:"card-date-picker",style:{marginLeft:"5px",borderBottom:"2px solid pink",marginBottom:"5px",padding:"10px 0"},children:["\ub9c8\uac10\uc77c: ",e.edit?(0,x.jsx)("input",{type:"date",name:"projectLimitTime",value:e.projectLimitTime,onChange:t=>R(t,e)}):e.projectLimitTime]}),(0,x.jsx)("div",{className:"text-end",children:e.edit?(0,x.jsxs)(x.Fragment,{children:[(0,x.jsx)(l.CMH,{className:"text-success me-2",onClick:()=>I(e)}),(0,x.jsx)(n.aqA,{className:"text-danger",onClick:()=>E(e)})]}):(0,x.jsx)(x.Fragment,{})})]})})})},e.projectNo))),(0,x.jsx)("div",{ref:q,className:"modal fade",id:"staticBackdrop","data-bs-backdrop":"static","data-bs-keyboard":"false",tabIndex:"-1","aria-labelledby":"staticBackdropLabel","aria-hidden":"true",children:(0,x.jsx)("div",{className:"modal-dialog",children:(0,x.jsxs)("div",{className:"modal-content",children:[(0,x.jsxs)("div",{className:"modal-header",children:[(0,x.jsx)("h1",{className:"modal-title fs-5",id:"staticBackdropLabel",children:"\uc0c8 \ud504\ub85c\uc81d\ud2b8"}),(0,x.jsx)("button",{type:"button",className:"btn-close","aria-label":"Close",onClick:A})]}),(0,x.jsxs)("div",{className:"modal-body",children:[(0,x.jsx)("div",{children:(0,x.jsxs)("p",{children:["\uc0ac\uc6d0 \ubc88\ud638: ",f.empNo]})}),(0,x.jsx)("div",{className:"row",children:(0,x.jsxs)("div",{className:"col",children:[(0,x.jsx)("label",{children:"\ud504\ub85c\uc81d\ud2b8 \uba85"}),(0,x.jsx)("input",{type:"text",name:"projectName",value:f.projectName,onChange:B,className:"form-control"})]})}),(0,x.jsx)("div",{className:"row",children:(0,x.jsxs)("div",{className:"col",children:[(0,x.jsx)("label",{children:"\uc2dc\uc791\uc77c"}),(0,x.jsx)("input",{type:"date",name:"projectStartTime",value:f.projectStartTime,onChange:B,className:"form-control"})]})}),(0,x.jsx)("div",{className:"row",children:(0,x.jsxs)("div",{className:"col",children:[(0,x.jsx)("label",{children:"\ub9c8\uac10\uc77c"}),(0,x.jsx)("input",{type:"date",name:"projectLimitTime",value:f.projectLimitTime,onChange:B,className:"form-control"})]})})]}),(0,x.jsxs)("div",{className:"modal-footer",children:[(0,x.jsx)("button",{className:"btn btn-success me-2",onClick:F,children:"\ub4f1\ub85d"}),(0,x.jsx)("button",{className:"btn btn-danger",onClick:A,children:"\ucde8\uc18c"})]})]})})})]})}}}]);
//# sourceMappingURL=366.01ae4151.chunk.js.map
// main.js - demo script mod
function onEnable(ctx) {
  ctx.log("Hello from script mod!");
  ctx.registerItem("dashspin:simpleblade", JSON.stringify({name:"Simple Blade", damage:10}));
}

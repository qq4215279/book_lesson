"use strict";

const { PI, cos, sin, atan2, sqrt, pow, abs, floor, random } = Math;
const rand = n => n * random();
const randRange = n => n - rand(2 * n);
const fadeInOut = (t, m) => {
  let hm = 0.5 * m;
  return abs((t + hm) % m - hm) / hm;
};
const dist = (x1, y1, x2, y2) => sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
const angle = (x1, y1, x2, y2) => atan2(y2 - y1, x2 - x1);

const baseUrl = 'img/';
const files = [
'cloud1.png',
'cloud2.png',
'cloud3.png',
'cloud4.png',
'cloud5.png'];

const maxSprites = 150;

let canvas,
ctx,
tick,
sprites,
clouds,
mouse,
origin;

function loadSprite(src) {
  return new Promise((resolve, reject) => {
    !src && reject('Error: No src provided');

    const sprite = new Image();

    sprite.src = src;
    sprite.addEventListener('load', () => resolve(sprite));
  });
}

function getCloud() {
  let sprite = sprites[floor(rand(sprites.length))],
  scale = rand(0.25) + 0.2,
  size = [scale * sprite.width, scale * sprite.height],
  position = [
  origin[0] + randRange(origin[0]),
  origin[1] + randRange(100) + 20],

  direction = angle(...origin, ...position);

  return {
    sprite,
    size,
    position,
    direction,
    speed: rand(0.15),
    destroy: false,
    life: 0,
    ttl: rand(200) + 100,
    update() {
      this.destroy = this.life++ > this.ttl;
      this.speed *= 1.005;
      this.position[0] += cos(this.direction) * this.speed;
      this.position[1] += sin(this.direction) * this.speed * 5;
      this.size[0] *= 1.005;
      this.size[1] *= 1.005;

      return this;
    },
    draw() {
      let x = this.position[0] - 0.5 * this.size[0],
      y = this.position[1] - 0.5 * this.size[1];

      ctx.a.save();
      ctx.a.globalAlpha = fadeInOut(this.life, this.ttl) * 0.25;
      ctx.a.drawImage(this.sprite, x, y, this.size[0], this.size[1]);
      ctx.a.globalCompositeOperation = 'difference';
      ctx.a.drawImage(this.sprite, x, y, this.size[0], this.size[1]);
      ctx.a.globalCompositeOperation = 'lighter';
      ctx.a.drawImage(this.sprite, x, y, this.size[0], this.size[1]);
      ctx.a.restore();

      return this;
    } };

}

function setup() {
  canvas = {
    a: document.createElement("canvas"),
    b: document.createElement("canvas") };

  canvas.b.style = `
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	`;
  document.body.appendChild(canvas.b);

  ctx = {
    a: canvas.a.getContext("2d"),
    b: canvas.b.getContext("2d") };


  tick = 0;

  sprites = [];
  clouds = [];
  origin = [];

  resize();

  Promise.all(files.map(file => loadSprite(`${baseUrl}${file}`))).then(_sprites => {
    sprites = _sprites;
    document.body.removeChild(document.querySelector('.loading'));
    draw();
  });
}

function resize() {
  const { innerWidth, innerHeight } = window;

  canvas.a.width = canvas.b.width = innerWidth;
  canvas.a.height = canvas.b.height = innerHeight;

  origin[0] = 0.5 * innerWidth;
  origin[1] = 0.5 * innerHeight;
}

function draw() {
  ctx.a.clearRect(0, 0, canvas.a.width, canvas.a.height);
  ctx.b.clearRect(0, 0, canvas.b.width, canvas.b.height);

  !(tick++ % 3) && clouds.length < maxSprites && clouds.push(getCloud());

  let i;

  for (i = clouds.length - 1; i >= 0; i--) {
    clouds[i].update().draw().destroy && clouds.splice(i, 1);
  }

  ctx.b.save();
  ctx.b.filter = 'blur(10px) brightness(200%)';
  ctx.b.drawImage(canvas.a, 0, 0);
  ctx.b.restore();

  ctx.b.save();
  ctx.b.globalAlpha = 0.75;
  ctx.b.drawImage(canvas.a, 0, 0);
  ctx.b.restore();

  window.requestAnimationFrame(draw);
}

window.addEventListener('resize', resize);
window.addEventListener('load', setup);
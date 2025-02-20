<script>
  import { createEventDispatcher, onDestroy } from 'svelte';
  import { tweened } from 'svelte/motion';
  import { linear as easing } from 'svelte/easing';
  import { fly } from 'svelte/transition';

  const dispatch = createEventDispatcher();

  export let countdown;

  let now = Date.now();
  let end = now + countdown * 1000;

  $: count = Math.round((end - now) / 1000);
  $: h = Math.floor(count / 3600);
  $: m = Math.floor((count - h * 3600) / 60);
  $: s = count - h * 3600 - m * 60;

  function updateTimer() {
    now = Date.now();
  }

  let interval = setInterval(updateTimer, 1000);
  $: if (count === 0) {
    clearInterval(interval);
    dispatch('clearTimer');
  }

  const duration = 1000;

  let offset = tweened(1, { duration, easing });
  let rotation = tweened(360, { duration, easing });

  $: offset.set(Math.max(count - 1, 0) / countdown);
  $: rotation.set((Math.max(count - 1, 0) / countdown) * 360);

  function handleStart() {
    now = Date.now();
    end = now + count * 1000;
    interval = setInterval(updateTimer, 1000);
    offset.set(Math.max(count - 1, 0) / countdown);
    rotation.set((Math.max(count - 1, 0) / countdown) * 360);
  }

  function padValue(value, length = 2, char = '0') {
    const { length: currentLength } = value.toString();
    if (currentLength >= length) return value.toString();
    return `${char.repeat(length - currentLength)}${value}`;
  }

  onDestroy(() => {
    clearInterval(interval);
  });
</script>

<main class="p-0 m-0 p-0 flex items-center justify-center h-screen w-full bg-green-main">
  <svg in:fly={{ y: -5 }} viewBox="-50 -50 100 100" width="70vw" height="70vw">
    <title>Remaining seconds: {count}</title>
    <g fill="none" stroke="currentColor" stroke-width="2">
      <circle stroke="var(--white-color-100)" r="46" />
      <path
        stroke="var(--yellow-main-color)"
        d="M 0 -46 a 46 46 0 0 0 0 92 46 46 0 0 0 0 -92"
        pathLength="1"
        stroke-dasharray="1"
        stroke-dashoffset={$offset}
      />
    </g>
    <g fill="var(--yellow-main-color)" stroke="none">
      <g transform="rotate({$rotation})">
        <g transform="translate(0 -46)">
          <circle r="4" />
        </g>
      </g>
    </g>

    <g
      fill="var(--white-color-100)"
      text-anchor="middle"
      dominant-baseline="baseline"
      font-size="13"
    >
      <text x="-3" y="6.5">
        {#each Object.entries({ h, m, s }) as [key, value], i}
          {#if countdown >= 60 ** (2 - i)}
            <tspan dx="3" font-weight="bold">{padValue(value)}</tspan><tspan dx="0.5" font-size="7"
              >{key}</tspan
            >
          {/if}
        {/each}
      </text>
    </g>
  </svg>
</main>

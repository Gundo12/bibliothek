/** @type {import("snowpack").SnowpackUserConfig} */
module.exports = {
  mount: {
    public: { url: '/', static: true },
    src: { url: '/dist' },
  },
  plugins: [
    [
      '@snowpack/plugin-typescript',
      {
        ...(process.versions.pnp ? { tsc: 'yarn pnpify tsc' } : {}),
      },
    ],
  ],
  devOptions: {
    port: 3000,
  },
  buildOptions: {
    htmlFragments: true,
  },
};

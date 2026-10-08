const tempInput = document.getElementById('temp');
const unitSelect = document.getElementById('unit');
const convertBtn = document.getElementById('convertBtn');
const errorEl = document.getElementById('error');
const resultEl = document.getElementById('result');

convertBtn.addEventListener('click', () => {
  errorEl.textContent = '';
  resultEl.innerHTML = '';

  const raw = tempInput.value.trim();
  const value = Number(raw);

  // Validation: empty or not a number
  if (raw === '' || isNaN(value)) {
    errorEl.textContent = 'Please enter a valid number.';
    return;
  }

  const unit = unitSelect.value;

  // Convert everything to Celsius first
  let celsius;
  if (unit === 'C') celsius = value;
  else if (unit === 'F') celsius = (value - 32) * 5 / 9;
  else celsius = value - 273.15;

  // Absolute zero check
  if (celsius < -273.15) {
    errorEl.textContent = 'Temperature cannot be below absolute zero (-273.15°C).';
    return;
  }

  const fahrenheit = celsius * 9 / 5 + 32;
  const kelvin = celsius + 273.15;

  resultEl.innerHTML =
    `<strong>${celsius.toFixed(2)} °C</strong><br>` +
    `<strong>${fahrenheit.toFixed(2)} °F</strong><br>` +
    `<strong>${kelvin.toFixed(2)} K</strong>`;
});
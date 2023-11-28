import 'intl-pluralrules';
import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';

// Import your language files
import en from '../locales/en/translation.json';
import no from '../locales/no/translation.json'

i18next
    .use(initReactI18next)
    .init({
        resources: {
            en: { translation: en },
            no: { translation: no }
        },
        lng: 'en', // default language
        fallbackLng: 'en',
        interpolation: { escapeValue: false }
    });

export default i18next;

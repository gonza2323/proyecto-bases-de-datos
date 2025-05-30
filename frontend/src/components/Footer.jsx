
import gitHubLogo from "../assets/github-logo.svg"

export const Footer = () => (
    <footer className="bg-red-500 px-14 py-6 text-center text-white">
        Creado por <a className="inline-flex gap-1.5 items-center hover:underline"
        href="https://github.com/gonza2323" target="_blank">gonza2323<img className="h-5" src={gitHubLogo} alt="Github Logo"/></a>
    </footer>
);

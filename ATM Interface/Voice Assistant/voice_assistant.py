import datetime
import speech_recognition as sr
import pyttsx3
import webbrowser

engine = pyttsx3.init()

def speak(text):
    print(f"Assistant: {text}")
    engine.say(text)
    engine.runAndWait()

def listen_command():
    recognizer = sr.Recognizer()
    with sr.Microphone() as source:
        print("\nListening for your command...")
        recognizer.adjust_for_ambient_noise(source, duration=1)
        try:
            audio = recognizer.listen(source, timeout=5, phrase_time_limit=5)
            print("Recognizing...")
            command = recognizer.recognize_google(audio)
            print(f"You said: {command}")
            return command.lower()
        except sr.WaitTimeoutError:
            print("Listening timed out.")
            return ""
        except sr.UnknownValueError:
            speak("Sorry, I could not understand what you said. Please repeat.")
            return ""
        except sr.RequestError:
            speak("Network error. Please check your internet connection.")
            return ""

def run_voice_assistant():
    speak("Hello! I am your voice assistant. How can I help you today?")
    
    while True:
        command = listen_command()
        
        if not command:
            continue
            
        if "hello" in command:
            speak("Hello! How are you doing?")
            
        elif "time" in command:
            current_time = datetime.datetime.now().strftime("%I:%M %p")
            speak(f"The current time is {current_time}")
            
        elif "date" in command:
            current_date = datetime.datetime.now().strftime("%B %d, %Y")
            speak(f"Today's date is {current_date}")
            
        elif "search" in command:
            speak("What would you like me to search for?")
            search_query = listen_command()
            if search_query:
                url = f"https://www.google.com/search?q={search_query}"
                webbrowser.open(url)
                speak(f"Here are the search results for {search_query}")
                
        elif "exit" in command or "quit" in command or "stop" in command:
            speak("Goodbye! Have a great day.")
            break
        else:
            speak("I am not sure how to help with that command. Try asking for the time, date, or to search something.")

if __name__ == "__main__":
    run_voice_assistant()
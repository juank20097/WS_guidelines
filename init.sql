CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'lineamientos') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE lineamientos');
   END IF;
END $$;

-- Crear la base de datos 'chatbot' si no existe
DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'chatbot_aligments') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE chatbot_aligments');
   END IF;
END $$;